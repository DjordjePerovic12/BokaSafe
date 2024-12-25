package llc.bokadev.bokasafe.core.base

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CancellationException
import llc.amplitudo.flourish_V2.core.utils.Constants.CHECK_CONNECTION
import llc.amplitudo.flourish_V2.core.utils.Constants.NETWORK_PROBLEM
import llc.bokadev.bokasafe.core.utils.Resource
import llc.bokadev.bokasafe.core.utils.toNonNull
import llc.bokadev.bokasafe.data.remote.dto.ApiErrorDto
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

open class BaseDataSource @Inject constructor(
    private val errorAdapter: JsonAdapter<ApiErrorDto>
) {



    protected suspend fun <T> retrieveResponse(
        call: suspend () -> Response<T>
    ) = try {
        Resource.Loading(null)
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            Resource.Success(data = body, statusCode = response.code())
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = errorAdapter.fromJson(errorBody.toNonNull())?.message
            Resource.Error(message = errorMessage, statusCode = response.code())

        }
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        e.printStackTrace()
        if (e is IOException) {
            Timber.e("Bad response: ${e.message}")
            Resource.Error(message = CHECK_CONNECTION)
        } else {
            Timber.e("Bad response: ${e.message}")
            Resource.Error(message = NETWORK_PROBLEM)
        }
    }


    fun <T : Any, E : Any> Resource<T>.mapTestResponse(mapperCallback: T.() -> E) =
        when (this) {
            is Resource.Success -> Resource.Success(
                data = this.data?.mapperCallback(), statusCode = this.statusCode
            )

            is Resource.Error -> Resource.Error(
                message = this.message, statusCode = this.statusCode
            )

            else -> Resource.Loading()
        }

    fun <T : Any, E : Any> Resource<T>.mapResponse(mapperCallback: T.() -> E) =
        when (this) {
            is Resource.Success -> Resource.Success(
                data = this.data?.mapperCallback(), statusCode = this.statusCode
            )

            is Resource.Error -> Resource.Error(
                message = this.message, statusCode = this.statusCode
            )

            else -> Resource.Loading()
        }

    protected suspend fun <T> retrieveResponseWithRetry(
        call: suspend () -> Response<T>,
        maxRetries: Int = 3
    ): Resource<T> {
        var attempt = 0
        while (attempt < maxRetries) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    return Resource.Success(data = body, statusCode = response.code())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorAdapter.fromJson(errorBody.toNonNull())?.message
                    return Resource.Error(message = errorMessage, statusCode = response.code())
                }
            } catch (e: IOException) {
                if (attempt == maxRetries - 1) {
                    e.printStackTrace()
                    return Resource.Error(message = CHECK_CONNECTION)
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
                return Resource.Error(message = NETWORK_PROBLEM)
            }
            attempt++
        }
        return Resource.Error(message = NETWORK_PROBLEM)
    }

}


