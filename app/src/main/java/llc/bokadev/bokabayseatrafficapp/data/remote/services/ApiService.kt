package llc.bokadev.bokabayseatrafficapp.data.remote.services

import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ElevationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("elevation/json")
   suspend fun getElevation(
        @Query("locations") locations: String?,  // e.g., "39.7391536,-104.9847034"
        @Query("key") apiKey: String?
    ): Response<ElevationResponse?>
}