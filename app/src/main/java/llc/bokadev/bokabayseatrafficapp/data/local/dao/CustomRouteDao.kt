package llc.bokadev.bokabayseatrafficapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import llc.bokadev.bokabayseatrafficapp.data.local.model.RouteEntity

@Dao
interface CustomRouteDao {

    @Insert
    suspend fun saveRoute(route: RouteEntity)

    @Upsert
    suspend fun editRoute(route: RouteEntity)

    @Delete
    suspend fun deleteRoute(route: RouteEntity)


    @Query("SELECT * FROM routeentity")
    fun getAllRoutes(): List<RouteEntity>

    @Query("SELECT * FROM routeentity WHERE id = :id ")
    fun getRouteById(id: Int) : RouteEntity


}