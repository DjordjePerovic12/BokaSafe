package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarineProtectedAreaDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "coordinates") val coordinates: List<CoordinateDto>,
    @Json(name = "mpaSymbolCoordinate") val mpaSymbolCoordinate: CoordinateDto,
    @Json(name = "anchoringProhibitedCoordinate") val anchoringProhibitedCoordinate: CoordinateDto,
    @Json(name = "fishingProhibited") val fishingProhibited: CoordinateDto,
    @Json(name = "isSelected") val isSelected: Boolean
)