package llc.bokadev.bokabayseatrafficapp.domain.model

import llc.bokadev.bokabayseatrafficapp.R

data class ShipWreck(
    val id: Int,
    val name: String,
    val description: String,
    val depth: String,
    val latitude: Double,
    val longitude: Double,
    var isSelected: Boolean,
    var iconResId: Int = R.drawable.ic_shipwreck,
)
