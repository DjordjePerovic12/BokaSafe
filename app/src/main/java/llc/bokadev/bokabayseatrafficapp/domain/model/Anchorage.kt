package llc.bokadev.bokabayseatrafficapp.domain.model

data class Anchorage(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val speedLimit: Int,
    var isSelected: Boolean

)
