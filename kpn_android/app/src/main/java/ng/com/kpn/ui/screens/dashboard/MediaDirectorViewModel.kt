package ng.com.kpn.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.com.kpn.core.network.Resource
import ng.com.kpn.data.remote.dto.MediaPipelineDto
import ng.com.kpn.data.repository.DashboardRepository
import javax.inject.Inject

data class MediaDashboardState(
    val isLoading: Boolean = false,
    val data: MediaPipelineDto? = null,
    val error: String? = null
)

@HiltViewModel
class MediaDirectorViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MediaDashboardState())
    val state: StateFlow<MediaDashboardState> = _state.asStateFlow()

    init {
        fetchMetrics()
    }

    fun fetchMetrics() {
        repository.getMediaPipelineMetrics().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                }
                is Resource.Success -> {
                    _state.value = MediaDashboardState(isLoading = false, data = result.data)
                }
                is Resource.Error -> {
                    _state.value = MediaDashboardState(isLoading = false, error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}
