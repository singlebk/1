package ng.com.kpn.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ng.com.kpn.api.KpnApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: ng.com.kpn.core.network.AuthInterceptor,
        tokenAuthenticator: ng.com.kpn.core.network.TokenAuthenticator
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            // Using localhost alias for Android emulator. Update with staging/prod URL when deploying.
            .baseUrl("http://10.0.2.2:8000/") 
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKpnApiService(retrofit: Retrofit): KpnApiService {
        return retrofit.create(KpnApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDashboardApiService(retrofit: retrofit2.Retrofit): ng.com.kpn.data.remote.api.DashboardApiService {
        return retrofit.create(ng.com.kpn.data.remote.api.DashboardApiService::class.java)
    }

}
