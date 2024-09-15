package llc.bokadev.bokabayseatrafficapp.core.base

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import llc.amplitudo.flourish_V2.core.utils.Constants.CHECK_CONNECTION
import llc.amplitudo.flourish_V2.core.utils.Constants.NETWORK_PROBLEM
import llc.bokadev.bokabayseatrafficapp.core.utils.Resource
import llc.bokadev.bokabayseatrafficapp.core.utils.toNonNull
import llc.bokadev.bokabayseatrafficapp.data.remote.dto.ApiErrorDto
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

open class BaseDataSource constructor(
    private val errorAdapter: JsonAdapter<ApiErrorDto>
) {
    protected suspend fun <T> retrieveFlow(
        call: suspend () -> Response<T>
    ) = flow {
        emit(Resource.Loading())
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                emit(Resource.Success(data = body, statusCode = response.code()))
                Timber.d("Successful response: $body")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = errorAdapter.fromJson(errorBody.toNonNull())?.message
                emit(Resource.Error(message = errorMessage, statusCode = response.code()))
                Timber.e("Bad response: $errorMessage")

            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            e.printStackTrace()
            if (e is IOException) {
                Timber.e("Bad response: ${e.message}")
                emit(Resource.Error(message = CHECK_CONNECTION))
            } else {
                Timber.e("Bad response: ${e.message}")
                emit(Resource.Error(message = NETWORK_PROBLEM))
            }
        }
    }.flowOn(Dispatchers.IO)

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


    fun <T : Any, E : Any> Flow<Resource<T>>.mapResponse(mapperCallback: T.() -> E) = this.map {
        when (it) {
            is Resource.Success -> Resource.Success(
                data = it.data?.mapperCallback(), statusCode = it.statusCode
            )

            is Resource.Error -> Resource.Error(
                message = it.message, statusCode = it.statusCode
            )

            else -> Resource.Loading()
        }
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
}


