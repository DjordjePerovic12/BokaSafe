package llc.bokadev.bokasafe.domain.repository

import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.domain.model.Checkpoint
import llc.bokadev.bokasafe.domain.model.Document
import llc.bokadev.bokasafe.domain.model.FishFarm

interface BokaSafeRepository {
    suspend fun getAllLighthouses(): Resource<List<Checkpoint>>

    suspend fun saveFcmToken(token: String)

    suspend fun getAllDocuments(): Resource<List<Document>>

    suspend fun getAllFishFarms(): Resource<List<FishFarm>>
}