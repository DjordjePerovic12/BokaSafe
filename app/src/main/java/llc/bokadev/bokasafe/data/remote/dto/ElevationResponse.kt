package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ElevationResponse(
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "status")
    val status: String
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "elevation")
    val elevation: Double,
    @Json(name = "location")
    val location: Location
)

@JsonClass(generateAdapter = true)
data class Location(
    val longitude: Double,
    val latitude: Double
)
