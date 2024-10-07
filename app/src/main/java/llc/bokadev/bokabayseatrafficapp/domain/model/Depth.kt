package llc.bokadev.bokabayseatrafficapp.domain.model

import com.google.android.gms.maps.model.LatLng

data class Depth(
    val depth: Double,
    val coordinates: LatLng
)