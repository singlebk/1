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

data class Opportunity(
    val id: String,
    val title: String,
    val organization: String,
    val type: String,
    val deadline: String,
    val isExternal: Boolean
)

data class OpportunitiesUiState(
    val isLoading: Boolean = true,
    val opportunities: List<Opportunity> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class OpportunitiesViewModel @Inject constructor(
    private val apiService: KpnApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(OpportunitiesUiState())
    val uiState: StateFlow<OpportunitiesUiState> = _uiState.asStateFlow()

    init {
        loadOpportunities()
    }

    private fun loadOpportunities() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                // TODO: Replace with real model deserialization
                // val response = apiService.getOpportunities() 
                
                // Simulating network response
                kotlinx.coroutines.delay(1000)
                val mockData = listOf(
                    Opportunity("1", "Kebbi State Youth Grant", "State Govt", "GRANT", "2024-06-30", false),
                    Opportunity("2", "Federal Tech Scholarship", "NITDA", "SCHOLARSHIP", "2024-07-15", true)
                )
                
                _uiState.value = OpportunitiesUiState(
                    isLoading = false,
                    opportunities = mockData
                )
            } catch (e: Exception) {
                _uiState.value = OpportunitiesUiState(
                    isLoading = false,
                    errorMessage = "Failed to load opportunities"
                )
            }
        }
    }
}
