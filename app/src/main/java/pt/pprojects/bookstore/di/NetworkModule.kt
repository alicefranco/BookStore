package pt.pprojects.bookstore.di

import android.util.Log.d
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pt.pprojects.bookstore.BuildConfig
import pt.pprojects.bookstore.network.ConnectionCheck
import pt.pprojects.network.ConnectionCheckInterface
import pt.pprojects.network.RetrofitBuilder
import pt.pprojects.network.error.NetworkingErrorMapper
import pt.pprojects.network.manager.NetworkManager
import pt.pprojects.network.manager.NetworkManagerInterface


private const val NETWORKING_ERROR_MAPPER = "NETWORKING_ERROR_MAPPER"
private const val HTTP_LOGGING_INTERCEPTOR = "HTTP_LOGGING_INTERCEPTOR"
private const val HTTP_LOGGING_INTERCEPTOR_WITH_API_KEY = "HTTP_LOGGING_INTERCEPTOR_WITH_API_KEY"
private const val KEY = "key"
private const val API_KEY = "AIzaSyD_vRLHUBkowVKAM653SLCMweJ_Ykw1gl0"

val networkModule = module {
    single<ConnectionCheckInterface> { ConnectionCheck(get()) }

    factory(named(NETWORKING_ERROR_MAPPER)) { NetworkingErrorMapper() }

    single<NetworkManagerInterface> {
        NetworkManager(
            connectionCheck = get(),
            networkingErrorMapper = get(named(NETWORKING_ERROR_MAPPER))
        )
    }

    factory<HttpLoggingInterceptor.Logger> {
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (BuildConfig.DEBUG) {
                    d("DEBUG", message)
                }
            }
        }
    }

    factory<Interceptor>(named(HTTP_LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor(get()).apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    factory<Interceptor>(named(HTTP_LOGGING_INTERCEPTOR_WITH_API_KEY)) {
        Interceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder().addQueryParameter(KEY, API_KEY).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named(HTTP_LOGGING_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(named(HTTP_LOGGING_INTERCEPTOR_WITH_API_KEY)))
            .build()
    }

    single {
        RetrofitBuilder(
            endpoint = BuildConfig.BASE_URL,
            httpClient = get()
        )
    }
}