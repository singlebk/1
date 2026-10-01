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
import ng.com.kpn.data.remote.dto.LgaDashboardDto
import ng.com.kpn.data.repository.DashboardRepository
import javax.inject.Inject

data class LgaState(
    val isLoading: Boolean = false,
    val data: LgaDashboardDto? = null,
    val error: String? = null
)

@HiltViewModel
class LgaViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LgaState())
    val state: StateFlow<LgaState> = _state.asStateFlow()

    init {
        fetchMetrics()
    }

    fun fetchMetrics() {
        repository.getLgaMetrics().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                is Resource.Success -> _state.value = LgaState(isLoading = false, data = result.data)
                is Resource.Error -> _state.value = LgaState(isLoading = false, error = result.message)
            }
        }.launchIn(viewModelScope)
    }
}
