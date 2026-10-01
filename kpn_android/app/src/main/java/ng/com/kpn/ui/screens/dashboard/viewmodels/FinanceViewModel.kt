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
import ng.com.kpn.data.remote.dto.FinanceDashboardDto
import ng.com.kpn.data.repository.DashboardRepository
import javax.inject.Inject

data class FinanceState(
    val isLoading: Boolean = false,
    val data: FinanceDashboardDto? = null,
    val error: String? = null
)

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FinanceState())
    val state: StateFlow<FinanceState> = _state.asStateFlow()

    init {
        fetchMetrics()
    }

    fun fetchMetrics() {
        repository.getFinanceMetrics().onEach { result ->
            when (result) {
                is Resource.Loading -> _state.value = _state.value.copy(isLoading = true, error = null)
                is Resource.Success -> _state.value = FinanceState(isLoading = false, data = result.data)
                is Resource.Error -> _state.value = FinanceState(isLoading = false, error = result.message)
            }
        }.launchIn(viewModelScope)
    }
}
