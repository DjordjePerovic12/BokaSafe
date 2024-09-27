package llc.bokadev.bokabayseatrafficapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ApiErrorDto
import llc.bokadev.bokabayseatrafficapp.data.remote.services.ApiService
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .dispatcher(dispatcher)


            okHttpClient.addInterceptor(
                HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
            )

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideSecurityService(
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideErrorAdapter(): JsonAdapter<ApiErrorDto> {
        return Moshi.Builder().build().adapter(ApiErrorDto::class.java).lenient()
    }


    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


}