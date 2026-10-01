package ng.com.kpn.ui.screens.public

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ng.com.kpn.api.KpnApiService
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val verifiedMembers: Int = 0,
    val activeWards: Int = 0,
    val impactCommunities: Int = 0,
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: KpnApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadImpactMetrics()
    }

    private fun loadImpactMetrics() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                // TODO: Replace with real model deserialization
                // val response = apiService.getImpactMetrics() 
                
                // Simulating network response for now to allow UI scaffolding
                kotlinx.coroutines.delay(1000)
                _uiState.value = HomeUiState(
                    isLoading = false,
                    verifiedMembers = 12450,
                    activeWards = 225,
                    impactCommunities = 148
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load network metrics"
                )
            }
        }
    }
}
