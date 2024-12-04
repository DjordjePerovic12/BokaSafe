package llc.bokadev.bokabayseatrafficapp.data.local.db

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


class LatLngAdapter {

    @ToJson
    fun toJson(latLng: LatLng): Map<String, Double> {
        return mapOf("latitude" to latLng.latitude, "longitude" to latLng.longitude)
    }

    @FromJson
    fun fromJson(json: Map<String, Double>): LatLng {
        return LatLng(json["latitude"]!!, json["longitude"]!!)
    }
}