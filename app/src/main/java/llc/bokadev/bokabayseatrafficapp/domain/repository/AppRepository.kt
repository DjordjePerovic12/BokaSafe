package llc.bokadev.bokabayseatrafficapp.domain.repository

import llc.bokadev.bokabayseatrafficapp.core.utils.Resource
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ElevationResponse

interface AppRepository {
    suspend fun getElevation(
        locations: String,
        apiKey: String
    ): Resource<ElevationResponse?>
}