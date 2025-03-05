package llc.bokadev.bokasafe.domain.model

import com.google.android.gms.maps.model.LatLng

data class Marina(
    val id: Int,
    val name: String,
    val coordinates: LatLng,
    var isSelected: Boolean
)
