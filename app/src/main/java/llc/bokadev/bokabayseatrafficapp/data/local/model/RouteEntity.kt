package llc.bokadev.bokabayseatrafficapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng


@Entity
data class RouteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val pointS: List<LatLng>,
    val distances: List<Float>,
    val totalDistance: Float,
    val azimuths: List<Float>
)
