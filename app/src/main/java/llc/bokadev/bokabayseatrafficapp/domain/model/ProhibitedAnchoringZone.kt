package llc.bokadev.bokabayseatrafficapp.domain.model

import com.google.android.gms.maps.model.LatLng

data class ProhibitedAnchoringZone(
    val id: Int,
    val name: String,
    val points: List<Pair<LatLng, LatLng>>,
    var isSelected: Boolean
)
