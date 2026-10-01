package ng.com.kpn.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ng.com.kpn.data.repository.AuthRepository
import ng.com.kpn.data.repository.Result
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState(errorMessage = "Username and password cannot be empty")
            return
        }

        viewModelScope.launch {
            repository.login(username, password).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = AuthUiState(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = AuthUiState(isSuccess = true)
                    }
                    is Result.Error -> {
                        _uiState.value = AuthUiState(errorMessage = result.message)
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
