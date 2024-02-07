package pt.pprojects.bookstore.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat
import pt.pprojects.network.ConnectionCheckInterface

class ConnectionCheck(context: Context) : ConnectionCheckInterface {

    private val connectivityService = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

    override fun hasInternetConnection(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityService?.activeNetwork ?: return false
            val capabilities = connectivityService.getNetworkCapabilities(network) ?: return false
            return capabilities.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
        return connectivityService?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}
