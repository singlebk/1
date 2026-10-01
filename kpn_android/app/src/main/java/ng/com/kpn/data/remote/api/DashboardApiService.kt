package ng.com.kpn.data.remote.api

import ng.com.kpn.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApiService {

    @GET("media/pipeline/")
    suspend fun getMediaPipelineMetrics(): Response<MediaPipelineDto>

    @GET("operations/oversight/")
    suspend fun getVpOversightMetrics(): Response<VPOversightDto>

    @GET("dashboards/secretariat/")
    suspend fun getSecretariatMetrics(): Response<SecretariatDashboardDto>

    @GET("dashboards/finance/")
    suspend fun getFinanceMetrics(): Response<FinanceDashboardDto>

    @GET("dashboards/zone/")
    suspend fun getZonalMetrics(): Response<ZonalDashboardDto>

    @GET("dashboards/lga/")
    suspend fun getLgaMetrics(): Response<LgaDashboardDto>

    @GET("dashboards/ward/")
    suspend fun getWardMetrics(): Response<WardDashboardDto>
}
