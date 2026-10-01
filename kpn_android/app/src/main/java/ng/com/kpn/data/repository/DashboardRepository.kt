package ng.com.kpn.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ng.com.kpn.core.network.Resource
import ng.com.kpn.data.remote.api.DashboardApiService
import ng.com.kpn.data.remote.dto.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val api: DashboardApiService
) {
    private inline fun <T> safeApiCall(crossinline apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message() ?: "Unknown error occurred", response.code()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Network error occurred"))
        }
    }

    fun getMediaPipelineMetrics(): Flow<Resource<MediaPipelineDto>> = safeApiCall { api.getMediaPipelineMetrics() }
    
    fun getVpOversightMetrics(): Flow<Resource<VPOversightDto>> = safeApiCall { api.getVpOversightMetrics() }

    fun getSecretariatMetrics(): Flow<Resource<SecretariatDashboardDto>> = safeApiCall { api.getSecretariatMetrics() }
    
    fun getFinanceMetrics(): Flow<Resource<FinanceDashboardDto>> = safeApiCall { api.getFinanceMetrics() }
    
    fun getZonalMetrics(): Flow<Resource<ZonalDashboardDto>> = safeApiCall { api.getZonalMetrics() }
    
    fun getLgaMetrics(): Flow<Resource<LgaDashboardDto>> = safeApiCall { api.getLgaMetrics() }
    
    fun getWardMetrics(): Flow<Resource<WardDashboardDto>> = safeApiCall { api.getWardMetrics() }
}
