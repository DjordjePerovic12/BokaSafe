package llc.bokadev.bokasafe.domain.model

import com.google.android.gms.maps.model.LatLng

data class FishFarm(
    val id: Int,
    val coordinates: List<LatLng>,
    val centralCoordinate: LatLng,
    var isSelected: Boolean
)