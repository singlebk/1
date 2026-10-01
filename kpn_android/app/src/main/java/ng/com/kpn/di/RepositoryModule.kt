package ng.com.kpn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.com.kpn.api.KpnApiService
import ng.com.kpn.data.local.SessionManager
import ng.com.kpn.data.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: KpnApiService,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepository(apiService, sessionManager)
    }
}
