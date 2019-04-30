package sk.cll.masterdetail.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class KUtils {
    companion object {
        @JvmStatic
        val BASE_URL = "https://uinames.com/"
        @JvmStatic
        val LIMIT = 15
        @JvmStatic
        val EXT = true

        @JvmStatic
        fun checkNetworkConnection(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.allNetworkInfo
            if (info != null) {
                for (inf in info) {
                    if (inf.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
            return false
        }
    }
}