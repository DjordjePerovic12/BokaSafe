package llc.bokadev.bokasafe.core.utils

enum class Gps {
    ON,
    OFF
}


enum class MapItems(val mapItemTypeId: Int) {
    LIGHTHOUSE(mapItemTypeId = 1),
    SHIPWRECK(mapItemTypeId = 2),
    PROHIBITED_ANCHORING_ZONE(mapItemTypeId = 3),
    ANCHORAGE(mapItemTypeId = 4),
    UNDERWATER_CABLE(mapItemTypeId = 5),
    BUOY(mapItemTypeId = 6),
    FISH_FARM(mapItemTypeId = 7),
    MARINE_PROTECTED_AREA(mapItemTypeId = 8),
}