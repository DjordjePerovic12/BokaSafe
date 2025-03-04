package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinateDto(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lng") val lng: Double
)
