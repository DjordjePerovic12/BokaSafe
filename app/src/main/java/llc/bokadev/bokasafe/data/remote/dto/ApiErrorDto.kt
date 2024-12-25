package llc.bokadev.bokasafe.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiErrorDto(
    @Json(name = "message")
    val message: String = ""

)