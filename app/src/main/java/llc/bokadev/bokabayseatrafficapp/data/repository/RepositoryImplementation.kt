package llc.bokadev.bokabayseatrafficapp.data.repository

import com.squareup.moshi.JsonAdapter
import llc.bokadev.bokabayseatrafficapp.core.base.BaseDataSource
import llc.bokadev.bokabayseatrafficapp.core.utils.Resource
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ApiErrorDto
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ElevationResponse
import llc.bokadev.bokabayseatrafficapp.data.remote.services.ApiService
import llc.bokadev.bokabayseatrafficapp.domain.repository.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImplementation @Inject constructor(
    private val apiService : ApiService,
    errorAdapter: JsonAdapter<ApiErrorDto>
) : AppRepository, BaseDataSource(errorAdapter) {


    override suspend fun getElevation(
        locations: String,
        apiKey: String
    ) = retrieveResponse {
        apiService.getElevation(
            locations = locations,
            apiKey = apiKey
        )
    }
}