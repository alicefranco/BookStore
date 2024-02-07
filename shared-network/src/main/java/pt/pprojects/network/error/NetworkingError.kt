package pt.pprojects.network.error

sealed class NetworkingError(override val message: String) : Exception() {

    object ConnectionTimeout : NetworkingError("Networking operation timed out.")

    object NoInternetConnection : NetworkingError("There is no internet connection.")

    object InvalidDataFormat : NetworkingError("Invalid response data format.")

    class UnknownEndpoint(
        override val cause: Throwable?
    ) : NetworkingError("Unknown endpoint. Cause: $cause")

    class UnknownNetworkException(
        override val cause: Throwable?
    ) : NetworkingError("Unknown network exception. Cause: $cause")
}
