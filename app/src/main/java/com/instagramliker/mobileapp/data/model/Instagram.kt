package com.instagramliker.mobileapp.data.model

import dev.niekirk.com.instagram4android.Instagram4Android

object Instagram {

    var instagram4Android: Instagram4Android? = null
        private set

    fun setInstagram(instagram: Instagram4Android) {
        instagram4Android = instagram
    }

}
