package llc.bokadev.bokasafe.data.local.db

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class LatLngConverter {

    @TypeConverter
    fun fromLatLng(latLng: LatLng?): String? {
        return if (latLng == null) null else "${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun toLatLng(value: String?): LatLng? {
        if (value.isNullOrEmpty()) return null

        val parts = value.split(",")
        return if (parts.size == 2) {
            LatLng(parts[0].toDouble(), parts[1].toDouble())
        } else {
            null
        }
    }
}
