package llc.bokadev.bokabayseatrafficapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import llc.amplitudo.flourish_V2.core.utils.Constants.DATASTORE_NAME
import llc.bokadev.bokabayseatrafficapp.core.utils.toNonNull
import llc.bokadev.bokabayseatrafficapp.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreImplementation @Inject constructor(@ApplicationContext val context: Context) :
    DataStoreRepository {

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    companion object {
        val PREFERRED_SPEED_UNIT = stringPreferencesKey("PREFERRED_SPEED_UNIT")
        val SHOULD_SHOW_CURSOR_INSTRUCTIONS = booleanPreferencesKey("SHOULD_SHOW_CURSOR_INSTRUCTIONS")
        private const val DEFAULT_SPEED_UNIT = "knots"
        private const val DEFAULT_SHOULD_SHOW_CURSOR_INSTRUCTIONS = true
    }

    override suspend fun savePreferredSpeedUnit(unit: String) {
        context.datastore.edit { datastore ->
            datastore[PREFERRED_SPEED_UNIT] = unit
        }
    }

    override suspend fun getPreferredSpeedUnit() = context.datastore.data.map { dataStore ->
        dataStore[PREFERRED_SPEED_UNIT] ?: DEFAULT_SPEED_UNIT
    }


    override suspend fun saveShouldShowCursorInstruction(shouldShow: Boolean) {
        context.datastore.edit { datastore ->
            datastore[SHOULD_SHOW_CURSOR_INSTRUCTIONS] = shouldShow
        }
    }

    override suspend fun getShouldShowCursorInstruction() = context.datastore.data.map { dataStore ->
        dataStore[SHOULD_SHOW_CURSOR_INSTRUCTIONS] ?: DEFAULT_SHOULD_SHOW_CURSOR_INSTRUCTIONS
    }
}