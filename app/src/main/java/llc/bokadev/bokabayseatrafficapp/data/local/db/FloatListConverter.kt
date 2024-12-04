package llc.bokadev.bokabayseatrafficapp.data.local.db

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class FloatListConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val type: Type = Types.newParameterizedType(List::class.java, Float::class.javaObjectType)
    private val adapter: JsonAdapter<List<Float>> = moshi.adapter(type)

    @TypeConverter
    fun fromFloatList(value: List<Float>?): String? {
        return if (value.isNullOrEmpty()) null else adapter.toJson(value)
    }

    @TypeConverter
    fun toFloatList(value: String?): List<Float>? {
        return if (value.isNullOrEmpty()) emptyList() else adapter.fromJson(value)
    }
}
