package com.calorify.app.helper

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager {
    companion object {
        fun isConnectedToNetwork(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}