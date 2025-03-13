package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarinaDto(
    @Json(name = "id") val id: Int,
    @Json(name = "coordinates") val coordinates: CoordinateDto,
    @Json(name = "isSelected") val isSelected: Boolean,
    @Json(name = "name") val name: String
)
