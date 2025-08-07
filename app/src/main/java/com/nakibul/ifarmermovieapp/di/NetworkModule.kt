package com.nakibul.ifarmermovieapp.di

import android.content.Context
import com.nakibul.ifarmermovieapp.data.datasource.MovieDataSource
import com.nakibul.ifarmermovieapp.data.datasourceImpl.MovieDataSourceImpl
import com.nakibul.ifarmermovieapp.data.local.GenreDao
import com.nakibul.ifarmermovieapp.data.local.MovieDao
import com.nakibul.ifarmermovieapp.data.remote.MovieApiService
import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import com.nakibul.ifarmermovieapp.domain.repositoryImpl.MovieRepositoryImpl
import com.nakibul.ifarmermovieapp.utils.Constant.BASE_URL
import com.nakibul.ifarmermovieapp.utils.Constant.PACKAGE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("App-Package-Name", PACKAGE_NAME)
                .build()
            chain.proceed(request)
        }

        // Cache configuration
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cacheDir = File(context.cacheDir, "http-cache")
        val cache = Cache(cacheDir, cacheSize.toLong())

        // Cache control interceptor for 15 minutes
        val cacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-Control", "public, max-age=900") // 15 minutes = 900 seconds
                .build()
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .addNetworkInterceptor(cacheInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Attach OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesMoviesRepository(
        movieDataSource: MovieDataSource,
        movieDao: MovieDao,
        genreDao: GenreDao
    ): MovieRepository {
        return MovieRepositoryImpl(movieDataSource, movieDao, genreDao)
    }

    @Singleton
    @Provides
    fun provideMoviesDataSource(moviesApiService: MovieApiService): MovieDataSource {
        return MovieDataSourceImpl(moviesApiService)
    }

}