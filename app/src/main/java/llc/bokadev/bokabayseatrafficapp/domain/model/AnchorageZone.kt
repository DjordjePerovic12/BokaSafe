package llc.bokadev.bokabayseatrafficapp.domain.model

import com.google.android.gms.maps.model.LatLng

data class AnchorageZone(
    val id: Int,
    val name: String,
    var isSelected: Boolean,
    val points: List<LatLng>
)
