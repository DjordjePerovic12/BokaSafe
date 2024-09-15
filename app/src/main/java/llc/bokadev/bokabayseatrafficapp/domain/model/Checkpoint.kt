package llc.bokadev.bokabayseatrafficapp.domain.model

import llc.bokadev.bokabayseatrafficapp.R

data class Checkpoint(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    var isSelected: Boolean,
    var iconResId: Int = R.drawable.ic_lighthouse,
)