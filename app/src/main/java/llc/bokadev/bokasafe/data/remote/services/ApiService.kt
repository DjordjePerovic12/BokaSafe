package llc.bokadev.bokasafe.data.remote.services

import llc.bokadev.bokasafe.data.remote.dto.DocumentDto
import llc.bokadev.bokasafe.data.remote.dto.LighthouseDto
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("/api/lighthouses")
    suspend fun getAllLighthouses() : Response<List<LighthouseDto>>

    @GET("api/documents")
    suspend fun getAllDocuments() : Response<List<DocumentDto>>

}