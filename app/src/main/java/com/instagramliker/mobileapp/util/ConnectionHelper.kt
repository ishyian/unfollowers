package com.instagramliker.mobileapp.util

import android.content.Context
import android.net.ConnectivityManager

object ConnectionHelper {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            (networkInfo != null && networkInfo.isConnected)
        } else false

    }
}