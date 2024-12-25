package llc.bokadev.bokasafe.domain.repository

import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.domain.model.Checkpoint

interface BokaSafeRepository {
    suspend fun getAllLighthouses() : Resource<List<Checkpoint>>

    suspend fun saveFcmToken(token: String)
}