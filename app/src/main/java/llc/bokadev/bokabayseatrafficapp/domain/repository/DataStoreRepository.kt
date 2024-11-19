package llc.bokadev.bokabayseatrafficapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun savePreferredSpeedUnit(unit: String)
    suspend fun getPreferredSpeedUnit(): Flow<String>
}