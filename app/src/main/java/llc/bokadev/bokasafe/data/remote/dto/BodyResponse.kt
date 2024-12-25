package llc.bokadev.bokasafe.data.remote.dto

data class BodyResponse<T>(
    val success: Boolean? = null,
    val message: String? = null,
    val data: T? = null
)
