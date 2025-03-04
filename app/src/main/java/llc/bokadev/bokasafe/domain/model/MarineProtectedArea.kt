package llc.bokadev.bokasafe.domain.model

import com.google.android.gms.maps.model.LatLng

data class MarineProtectedArea(
    val id: Int,
    val name: String,
    val coordinates: List<LatLng>,
    val mpaSymbolCoordinate: LatLng,
    val anchoringProhibitedCoordinate: LatLng,
    val fishingProhibited: LatLng,
    var isSelected: Boolean
)