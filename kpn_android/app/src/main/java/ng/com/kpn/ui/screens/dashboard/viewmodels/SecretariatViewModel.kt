package ng.com.kpn.ui.screens.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.com.kpn.core.network.Resource
import ng.com.kpn.data.remote.dto.SecretariatDashboardDto
import ng.com.kpn.data.repository.DashboardRepository
import javax.inject.Inject

data class SecretariatState(
    val isLoading: Boolean = false,
    val data: SecretariatDashboardDto? = null,
    val error: String? = null
)

@HiltViewModel
class SecretariatViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SecretariatState())
    val state: StateFlow<SecretariatState> = _state.asStateFlow()

    init {
        fetchMetrics()
    }

    fun fetchMetrics() {
        repository.getSecretariatMetrics().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                is Resource.Success -> _state.value = SecretariatState(isLoading = false, data = result.data)
                is Resource.Error -> _state.value = SecretariatState(isLoading = false, error = result.message)
            }
        }.launchIn(viewModelScope)
    }
}
