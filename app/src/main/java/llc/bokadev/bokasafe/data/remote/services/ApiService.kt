package llc.bokadev.bokasafe.data.remote.services

import llc.bokadev.bokasafe.data.remote.dto.DocumentDto
import llc.bokadev.bokasafe.data.remote.dto.FishFarmDto
import llc.bokadev.bokasafe.data.remote.dto.LighthouseDto
import llc.bokadev.bokasafe.data.remote.dto.MarinaDto
import llc.bokadev.bokasafe.data.remote.dto.MarineProtectedAreaDto
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("/api/lighthouses")
    suspend fun getAllLighthouses() : Response<List<LighthouseDto>>

    @GET("api/documents")
    suspend fun getAllDocuments() : Response<List<DocumentDto>>

    @GET("api/fishfarms")
    suspend fun getAllFishFarms() : Response<List<FishFarmDto>>

    @GET("api/marineProtectedAreas")
    suspend fun getAllMarineProtectedAreas() : Response<List<MarineProtectedAreaDto>>

    @GET("api/marinas")
    suspend fun getAllMarinas() : Response<List<MarinaDto>>

}