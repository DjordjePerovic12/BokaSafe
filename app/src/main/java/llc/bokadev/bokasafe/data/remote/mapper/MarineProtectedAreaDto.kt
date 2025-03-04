package llc.bokadev.bokasafe.data.remote.mapper

import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokasafe.data.remote.dto.FishFarmDto
import llc.bokadev.bokasafe.data.remote.dto.MarineProtectedAreaDto
import llc.bokadev.bokasafe.domain.model.FishFarm
import llc.bokadev.bokasafe.domain.model.MarineProtectedArea

fun List<MarineProtectedAreaDto>.toMarineProtectedAreas(): List<MarineProtectedArea> {
    return this.map { marineProtectedArea ->
        MarineProtectedArea(
            id = marineProtectedArea.id,
            name = marineProtectedArea.name,
            coordinates = marineProtectedArea.coordinates.map {
                LatLng(it.lat, it.lng)
            },
            isSelected = marineProtectedArea.isSelected,
            mpaSymbolCoordinate = LatLng(
                marineProtectedArea.mpaSymbolCoordinate.lat,
                marineProtectedArea.mpaSymbolCoordinate.lng
            ),
            anchoringProhibitedCoordinate = LatLng(
                marineProtectedArea.anchoringProhibitedCoordinate.lat,
                marineProtectedArea.anchoringProhibitedCoordinate.lng
            ),
            fishingProhibited = LatLng(
                marineProtectedArea.fishingProhibited.lat,
                marineProtectedArea.fishingProhibited.lng
            ),
        )
    }
}