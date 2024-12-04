package llc.bokadev.bokabayseatrafficapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import llc.bokadev.bokabayseatrafficapp.data.local.dao.CustomRouteDao
import llc.bokadev.bokabayseatrafficapp.data.local.model.RouteEntity

@Database(
    entities = [RouteEntity::class],
    version = 1,
    exportSchema = false
)
    @TypeConverters(
        LatLngConverter::class,
        LatLngListConverter::class,
        FloatListConverter::class
    )
abstract class BokaBaySeaTrafficAppDatabase : RoomDatabase() {

    abstract val customRouteDao: CustomRouteDao
}