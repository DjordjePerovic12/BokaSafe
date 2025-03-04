package llc.bokadev.bokasafe.domain.model

data class MapItemFilters(
    val lighthouses: Boolean,
    val shipwrecks: Boolean,
    val prohibitedAnchoringZone: Boolean,
    val anchorages: Boolean,
    val underwaterCables: Boolean,
    val buoys: Boolean,
    val fishFarms: Boolean,
    val marineProtectedAreas: Boolean
)
