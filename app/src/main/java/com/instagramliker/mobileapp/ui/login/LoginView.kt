package com.instagramliker.mobileapp.ui.login

import com.arellomobile.mvp.MvpView
import dev.niekirk.com.instagram4android.Instagram4Android

interface LoginView: MvpView {

    fun showProgress()
    fun hideProgress()
    fun onLoginSuccess(instagram: Instagram4Android)
    fun onError(message: String)
}