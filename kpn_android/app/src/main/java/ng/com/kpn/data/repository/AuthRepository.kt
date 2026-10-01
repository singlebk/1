package ng.com.kpn.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.com.kpn.api.KpnApiService
import ng.com.kpn.data.local.SessionManager
import javax.inject.Inject

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

class AuthRepository @Inject constructor(
    private val apiService: KpnApiService,
    private val sessionManager: SessionManager
) {
    // Note: Actual login parameters will map to Django's JWT Login request.
    // We are establishing the architecture pattern here per Phase 2 rules.
    
    fun login(username: String, password: String): Flow<Result<Unit>> = flow {
        emit(Result.Loading)
        try {
            // TODO: Execute apiService.login() when models are generated
            // val response = apiService.login(LoginRequest(username, password))
            // sessionManager.saveAuthToken(response.access)
            // sessionManager.saveRefreshToken(response.refresh)
            
            // Simulating network delay for architecture verification
            kotlinx.coroutines.delay(1500)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Authentication failed"))
        }
    }

    fun logout() {
        sessionManager.clearSession()
    }
    
    fun isLoggedIn(): Boolean {
        return sessionManager.getAuthToken() != null
    }
}
