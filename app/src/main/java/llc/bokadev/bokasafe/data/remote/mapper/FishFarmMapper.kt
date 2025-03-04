package llc.bokadev.bokasafe.data.remote.mapper

import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokasafe.data.remote.dto.FishFarmDto
import llc.bokadev.bokasafe.domain.model.FishFarm

fun List<FishFarmDto>.toFishFarms(): List<FishFarm> {
    return this.map { fishfarm ->
        FishFarm(
            id = fishfarm.id,
            coordinates = fishfarm.coordinates.map {
                LatLng(it.lat, it.lng)
            },
            centralCoordinate = LatLng(
                fishfarm.centralCoordinate.lat,
                fishfarm.centralCoordinate.lng
            ),
            isSelected = fishfarm.isSelected
        )
    }
}