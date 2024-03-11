package com.example.ideamagixassignment.shopapp.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.ideamagixassignment.shopapp.presentation.profile.auth.AuthService
import java.time.Duration
import javax.inject.Singleton

private const val BASE_URL = "https://fakestoreapi.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val duration = Duration.ofSeconds(60)
        return OkHttpClient.Builder()
            .connectTimeout(duration)
            .readTimeout(duration)
            .writeTimeout(duration)
            .build()
    }

    @Provides
    @Singleton
    fun providesProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

}