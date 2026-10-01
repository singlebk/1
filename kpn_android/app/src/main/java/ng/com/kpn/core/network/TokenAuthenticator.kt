package ng.com.kpn.core.network

import ng.com.kpn.data.local.SessionManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val sessionManager: SessionManager
) : Authenticator {
    
    override fun authenticate(route: Route?, response: Response): Request? {
        // If the request already failed with an Authorization header, the token is invalid/expired
        if (response.request.header("Authorization") != null) {
            
            // Prevent infinite loops if the refresh token is also invalid
            if (response.priorResponse != null) {
                sessionManager.clearSession()
                return null 
            }
            
            val refreshToken = sessionManager.getRefreshToken() ?: return null
            
            synchronized(this) {
                // TODO (Track B): Call POST /api/v1/auth/refresh/ synchronously here using OkHttp directly
                // If successful:
                // val newAccessToken = "fetched_token"
                // sessionManager.saveAuthToken(newAccessToken)
                // return response.request.newBuilder()
                //     .header("Authorization", "Bearer $newAccessToken")
                //     .build()
                
                // If refresh fails, clear session and force re-login
                // sessionManager.clearSession()
            }
        }
        return null
    }
}
