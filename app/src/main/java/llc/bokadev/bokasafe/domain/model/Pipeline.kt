package llc.bokadev.bokasafe.domain.model

import com.google.android.gms.maps.model.LatLng

data class Pipeline(
    val id : Int,
    val name: String,
    val points: List<LatLng>
)
