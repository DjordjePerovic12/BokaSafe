package llc.bokadev.bokasafe.data.remote.mapper

import llc.bokadev.bokasafe.data.remote.dto.DocumentDto
import llc.bokadev.bokasafe.data.remote.dto.LighthouseDto
import llc.bokadev.bokasafe.domain.model.Checkpoint
import llc.bokadev.bokasafe.domain.model.Document

fun List<DocumentDto>.toDocument(): List<Document> {
    return this.map { document ->
        Document(
            id = document.id,
            name = document.name,
            url = document.url
        )
    }
}