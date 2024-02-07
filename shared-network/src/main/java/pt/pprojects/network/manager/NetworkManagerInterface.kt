package pt.pprojects.network.manager

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NetworkManagerInterface {
    fun performAndDone(request: Completable): Completable
    fun <Data : Any> performAndReturnsData(request: Single<Data>): Single<Data>
}
