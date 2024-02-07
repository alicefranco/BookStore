package pt.pprojects.network

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val gsonFactory = GsonConverterFactory.create()
    private val adapterFactory = RxJava3CallAdapterFactory.create()

    operator fun invoke(
        endpoint: String,
        httpClient: OkHttpClient,
        convertFactory: Converter.Factory = gsonFactory,
        callAdapterFactory: RxJava3CallAdapterFactory = adapterFactory,
    ) = with(Retrofit.Builder()) {
        baseUrl(endpoint)
        client(httpClient)
        addCallAdapterFactory(callAdapterFactory)
        addConverterFactory(convertFactory)
        build()
    }
}
