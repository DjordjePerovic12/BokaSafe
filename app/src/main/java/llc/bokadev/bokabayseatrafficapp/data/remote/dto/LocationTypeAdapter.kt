package llc.bokadev.bokabayseatrafficapp.data.remote.dto

import android.location.Location
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.ToJson


class LocationAdapter {
    // Serialize (convert Location to JSON)
    @ToJson
    @Throws(Exception::class)
    fun toJson(writer: JsonAdapter<Any?>, location: Location) {
        writer.toJsonValue(
            LocationJson(
                location.latitude,
                location.longitude,
                location.altitude,
                location.accuracy,
                location.provider
            )
        )
    }

    // Deserialize (convert JSON to Location)
    @FromJson
    fun fromJson(locationJson: LocationJson): Location {
        val location = Location(locationJson.provider)
        location.latitude = locationJson.latitude
        location.longitude = locationJson.longitude
        location.altitude = locationJson.altitude
        location.accuracy = locationJson.accuracy
        return location
    }

    // Helper class to represent Location in JSON
    class LocationJson(
        @field:Json(name = "latitude") var latitude: Double, @field:Json(
            name = "longitude"
        ) var longitude: Double, @field:Json(name = "altitude") var altitude: Double, @field:Json(
            name = "accuracy"
        ) var accuracy: Float, @field:Json(name = "provider") var provider: String?
    )
}

