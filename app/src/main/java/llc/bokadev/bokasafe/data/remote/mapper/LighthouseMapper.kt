package llc.bokadev.bokasafe.data.remote.mapper

import llc.bokadev.bokasafe.data.remote.dto.LighthouseDto
import llc.bokadev.bokasafe.domain.model.Checkpoint

fun List<LighthouseDto>.toLighthouse(): List<Checkpoint> {
    return this.map { lighthouse ->
        Checkpoint(
            id = lighthouse.id,
            name = lighthouse.name,
            latitude = lighthouse.latitude,
            longitude = lighthouse.longitude,
            isSelected = lighthouse.isSelected,
            status = lighthouse.status,
            characteristics = lighthouse.characteristics
        )
    }
}