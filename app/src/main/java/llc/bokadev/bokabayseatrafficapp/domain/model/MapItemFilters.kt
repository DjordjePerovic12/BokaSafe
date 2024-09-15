package llc.bokadev.bokabayseatrafficapp.domain.model

data class MapItemFilters(
    val lighthouses: Boolean,
    val shipwrecks: Boolean,
    val prohibitedAnchoringZone: Boolean,
    val anchorages: Boolean,
    val underwaterCables: Boolean,
    val buoys: Boolean
)
