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

data class Article(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
    val publishedDate: String,
    val isVerified: Boolean
)

data class NewsroomUiState(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class NewsroomViewModel @Inject constructor(
    private val apiService: KpnApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsroomUiState())
    val uiState: StateFlow<NewsroomUiState> = _uiState.asStateFlow()

    init {
        loadNewsroom()
    }

    private fun loadNewsroom() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                // TODO: Replace with real model deserialization
                // val response = apiService.getNewsroom() 
                
                // Simulating network response for architecture setup
                kotlinx.coroutines.delay(1000)
                val mockData = listOf(
                    Article("1", "KPN Empowers 500 Youth", "New skills acquisition program launched.", "YOUTH", "2024-05-12", true),
                    Article("2", "Statewide Community Outreach", "Our teams visited rural wards.", "COMMUNITY", "2024-05-10", true),
                    Article("3", "Townhall Meeting Scheduled", "Join us for the upcoming session.", "CIVIC", "2024-05-08", false)
                )
                
                _uiState.value = NewsroomUiState(
                    isLoading = false,
                    articles = mockData
                )
            } catch (e: Exception) {
                _uiState.value = NewsroomUiState(
                    isLoading = false,
                    errorMessage = "Failed to load newsroom articles"
                )
            }
        }
    }
}
