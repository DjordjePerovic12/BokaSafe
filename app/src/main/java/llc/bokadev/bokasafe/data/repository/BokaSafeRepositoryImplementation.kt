package llc.bokadev.bokasafe.data.repository

import com.squareup.moshi.JsonAdapter
import llc.bokadev.bokasafe.core.base.BaseDataSource
import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.data.remote.dto.ApiErrorDto
import llc.bokadev.bokasafe.data.remote.mapper.toDocument
import llc.bokadev.bokasafe.data.remote.mapper.toLighthouse
import llc.bokadev.bokasafe.data.remote.services.ApiService
import llc.bokadev.bokasafe.domain.model.Checkpoint
import llc.bokadev.bokasafe.domain.model.Document
import llc.bokadev.bokasafe.domain.repository.BokaSafeRepository
import llc.bokadev.bokasafe.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BokaSafeRepositoryImplementation @Inject constructor(
    private val apiService : ApiService,
    errorAdapter: JsonAdapter<ApiErrorDto>,
    private val dataStore: DataStoreRepository
) : BokaSafeRepository, BaseDataSource(errorAdapter) {

    override suspend fun getAllLighthouses(): Resource<List<Checkpoint>> = retrieveResponse {
        apiService.getAllLighthouses()
    }.mapResponse { toLighthouse() }

    override suspend fun saveFcmToken(token: String) {
        dataStore.saveFcmToken(token)
    }

    override suspend fun getAllDocuments() : Resource<List<Document>> = retrieveResponse {
        apiService.getAllDocuments()
    }.mapResponse { toDocument() }
}