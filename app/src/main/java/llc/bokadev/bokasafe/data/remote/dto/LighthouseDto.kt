package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LighthouseDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "isSelected") val isSelected: Boolean,
    @Json(name = "status") val status: String,
    @Json(name = "characteristics") val characteristics: String
)