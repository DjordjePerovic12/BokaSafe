package llc.bokadev.bokasafe.domain.repository

import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.domain.model.Checkpoint
import llc.bokadev.bokasafe.domain.model.Document
import llc.bokadev.bokasafe.domain.model.FishFarm
import llc.bokadev.bokasafe.domain.model.Marina
import llc.bokadev.bokasafe.domain.model.MarineProtectedArea

interface BokaSafeRepository {
    suspend fun getAllLighthouses(): Resource<List<Checkpoint>>

    suspend fun saveFcmToken(token: String)

    suspend fun getAllDocuments(): Resource<List<Document>>

    suspend fun getAllFishFarms(): Resource<List<FishFarm>>

    suspend fun getAllMarineProtectedAreas(): Resource<List<MarineProtectedArea>>

    suspend fun getAllMarinas(): Resource<List<Marina>>
}