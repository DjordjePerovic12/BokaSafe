package llc.bokadev.bokabayseatrafficapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun savePreferredSpeedUnit(unit: String)
    suspend fun getPreferredSpeedUnit(): Flow<String>

    suspend fun saveShouldShowCursorInstruction(shouldShow: Boolean)
    suspend fun getShouldShowCursorInstruction(): Flow<Boolean>
}