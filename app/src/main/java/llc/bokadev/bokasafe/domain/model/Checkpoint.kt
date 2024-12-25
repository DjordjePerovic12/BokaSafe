package llc.bokadev.bokasafe.domain.model

import llc.bokadev.bokasafe.R

data class Checkpoint(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val characteristics: String,
    val status: String,
    var isSelected: Boolean,
    var iconResId: Int = R.drawable.light_purple,
)