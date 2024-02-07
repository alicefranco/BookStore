package pt.pprojects.network.error

import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkingErrorMapper {
   fun mapThrowableToNetworkingError(cause: Throwable): NetworkingError {
        return when (cause) {
            is NullPointerException -> NetworkingError.InvalidDataFormat
            is SocketTimeoutException -> NetworkingError.ConnectionTimeout
            is UnknownHostException -> NetworkingError.UnknownEndpoint(cause)
            else -> NetworkingError.UnknownNetworkException(cause)
        }
    }
}
