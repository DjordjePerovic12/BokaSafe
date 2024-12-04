package llc.bokadev.bokabayseatrafficapp.data.local.db

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class LatLngListConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val type: Type = Types.newParameterizedType(List::class.java, LatLng::class.java)
    private val adapter: JsonAdapter<List<LatLng>> = moshi.adapter(type)

    @TypeConverter
    fun fromLatLngList(value: List<LatLng>?): String? {
        return if (value.isNullOrEmpty()) null else adapter.toJson(value)
    }

    @TypeConverter
    fun toLatLngList(value: String?): List<LatLng>? {
        return if (value.isNullOrEmpty()) emptyList() else adapter.fromJson(value)
    }
}
