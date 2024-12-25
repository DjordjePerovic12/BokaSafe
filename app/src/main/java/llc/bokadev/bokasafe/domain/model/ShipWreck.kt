package llc.bokadev.bokasafe.domain.model

import llc.bokadev.bokasafe.R

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
