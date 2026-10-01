package ng.com.kpn.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body

/**
 * KPN REST API Interface.
 * Mapped exactly to the discovered endpoints in rest_api/urls.py.
 * Missing endpoints are documented as BACKEND_API_REQUIRED.
 */
interface KpnApiService {
    
    // ==========================================
    // PUBLIC ENDPOINTS (VERIFIED)
    // ==========================================
    
    @GET("api/v1/newsroom/")
    suspend fun getNewsroom(): Any // TODO: Map to CampaignResponse model

    @GET("api/v1/opportunities/")
    suspend fun getOpportunities(@Query("category") category: String? = null): Any
    
    @GET("api/v1/impact/")
    suspend fun getImpactMetrics(): Any
    
    @GET("api/v1/leadership-directory/")
    suspend fun getLeadershipDirectory(): Any

    @GET("api/v1/roles/")
    suspend fun getRoles(@Query("tier") tier: String? = null): Any
    
    // ==========================================
    // AUTHENTICATION (VERIFIED)
    // ==========================================
    
    @POST("api/v1/auth/login/")
    suspend fun login(@Body request: Any): Any // TODO: LoginRequest/Response models
    
    @POST("api/v1/auth/register/")
    suspend fun register(@Body request: Any): Any
    
    @GET("api/v1/auth/me/")
    suspend fun getMe(): Any
    
    // ==========================================
    // DASHBOARD & WORKFLOW (BACKEND_API_REQUIRED)
    // ==========================================
    
    /* 
     * The following administrative workflows exist in Django views but lack REST APIs.
     * They are documented here as contracts that the backend team must fulfill.
     * 
     * @POST("api/v1/devices/register/") // FCM Token Registration
     * @GET("api/v1/members/pending/") // Member Approval Queue
     * @POST("api/v1/members/{id}/approve/") // Approve Member Action
     * @GET("api/v1/meetings/ward/") // Ward Meetings Logbook
     * @POST("api/v1/reports/community/") // Submit Community Report
     * @GET("api/v1/finance/summary/") // Treasurer Dashboard Metrics
     * @GET("api/v1/media/pending/") // Media Director Pending Queue
     */
}
