package llc.bokadev.bokabayseatrafficapp.domain.model

import com.google.android.gms.maps.model.LatLng

data class UnderwaterCable(
    val id: Int,
    val name: String,
    val coordinates: Pair<LatLng, LatLng>,
    val isSelected: Boolean
)
