package llc.bokadev.bokabayseatrafficapp.domain.repository

import kotlinx.coroutines.flow.Flow
import android.location.Location

interface LocationRepository {

    fun getLocationUpdates(): Flow<Location>

    class LocationException(message: String) : Exception()
}