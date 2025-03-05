package llc.bokadev.bokasafe.data.remote.mapper

import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokasafe.data.remote.dto.MarinaDto
import llc.bokadev.bokasafe.data.remote.dto.MarineProtectedAreaDto
import llc.bokadev.bokasafe.domain.model.Marina
import llc.bokadev.bokasafe.domain.model.MarineProtectedArea

fun List<MarinaDto>.toMarinas(): List<Marina> {
    return this.map { marina ->
        Marina(
            id = marina.id,
            name = marina.name,
            coordinates = LatLng(marina.coordinates.lat, marina.coordinates.lng),
            isSelected = marina.isSelected,
        )
    }
}