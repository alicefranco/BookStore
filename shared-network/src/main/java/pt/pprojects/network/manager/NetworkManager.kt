package pt.pprojects.network.manager

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import pt.pprojects.network.ConnectionCheckInterface
import pt.pprojects.network.error.NetworkingError
import pt.pprojects.network.error.NetworkingErrorMapper

class NetworkManager(
    private val connectionCheck: ConnectionCheckInterface,
    private val networkingErrorMapper: NetworkingErrorMapper
) : NetworkManagerInterface {

    override fun performAndDone(request: Completable): Completable {
        return when (connectionCheck.hasInternetConnection()) {
            false -> Completable.error(NetworkingError.NoInternetConnection)
            true -> request
                .onErrorResumeNext { cause ->
                    Completable.error(networkingErrorMapper.mapThrowableToNetworkingError(cause))
                }
        }
    }

    override fun <Data : Any> performAndReturnsData(request: Single<Data>): Single<Data> {
        return when (connectionCheck.hasInternetConnection()) {
            false -> Single.error(NetworkingError.NoInternetConnection)
            true -> request
                .onErrorResumeNext { cause -> Single.error(networkingErrorMapper.mapThrowableToNetworkingError(cause)) }
        }
    }
}
