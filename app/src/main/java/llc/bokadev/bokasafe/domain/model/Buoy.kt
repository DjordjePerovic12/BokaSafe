package llc.bokadev.bokasafe.domain.model

import com.google.android.gms.maps.model.LatLng

data class Buoy(
    val id: Int,
    val name: String,
    val characteristic: String,
    var isSelected: Boolean,
    val coordinates: LatLng
)
