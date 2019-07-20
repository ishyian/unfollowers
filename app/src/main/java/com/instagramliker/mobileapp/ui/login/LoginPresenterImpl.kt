package com.instagramliker.mobileapp.ui.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Completable
import dev.niekirk.com.instagram4android.Instagram4Android
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import android.R


@InjectViewState
class LoginPresenterImpl : MvpPresenter<LoginView>(), LoginPresenter {

    private lateinit var disposable: Disposable
    override fun onLoginBtnClicked(username: String, password: String) {

        loginToInstagram(username, password)

    }


    private fun loginToInstagram(username: String, password: String) {

        val instagram = Instagram4Android.builder().username(username).password(password).build()

        disposable = attemptLogin(instagram)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ viewState.showProgress()}
            .subscribe { instagramLoginResult ->
                viewState.hideProgress()
                if (instagramLoginResult.status == "ok") {
                    viewState.onLoginSuccess(instagram)
                } else {
                    viewState.onError(instagramLoginResult.message)
                }

            }

    }

    private fun attemptLogin(instagram: Instagram4Android): Observable<InstagramLoginResult> {

        return Observable.create { observableEmitter ->

            instagram.setup()
            observableEmitter.onNext(instagram.login())

        }

    }

    fun onDestroyPresenter() {
        disposable.dispose()
    }
}