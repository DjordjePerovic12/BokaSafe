package llc.bokadev.bokasafe.data.remote.dto

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FishFarmDto(
    @Json(name = "id") val id: Int,
    @Json(name = "coordinates") val coordinates: List<CoordinateDto>,
    @Json(name = "centralCoordinate") val centralCoordinate: CoordinateDto,
    @Json(name = "isSelected") val isSelected: Boolean
)