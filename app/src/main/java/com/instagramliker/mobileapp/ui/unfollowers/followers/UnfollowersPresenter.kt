package com.instagramliker.mobileapp.ui.unfollowers.followers

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.instagramliker.mobileapp.data.model.UserProfile
import dev.niekirk.com.instagram4android.Instagram4Android
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowingRequest
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest
import dev.niekirk.com.instagram4android.requests.InstagramUnfollowRequest
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*

@InjectViewState
class UnfollowersPresenter : MvpPresenter<UnfollowersView>() {


    private val disposable = CompositeDisposable()
    private var instagram: Instagram4Android? = null

    fun getUnfollowers() {
        disposable.add(
            getUserUnfollowers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doOnTerminate { viewState.hideProgress() }
                .subscribe {
                    viewState.updateUnfollowers(it)
                }
        )

    }

    private fun getUserUnfollowers(): Observable<List<UserProfile>> {

        return Observable.create { observableEmitter ->

            val unfollowersProfiles = ArrayList<UserProfile>()

            var usernameResult: InstagramSearchUsernameResult?
            var followers: List<InstagramUserSummary>? = null
            var following: List<InstagramUserSummary>? = null

            try {
                usernameResult = instagram!!.sendRequest(InstagramSearchUsernameRequest(instagram!!.username))
                followers =
                    instagram!!.sendRequest(InstagramGetUserFollowersRequest(usernameResult!!.user.getPk())).getUsers()
                following =
                    instagram!!.sendRequest(InstagramGetUserFollowingRequest(usernameResult.user.getPk())).getUsers()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            for (person in following!!) {
                if (!followers!!.contains(person)) {
                    val user = UserProfile(
                        person.getUsername(),
                        person.getFull_name(),
                        person.getProfile_pic_url(),
                        person.is_verified()
                    )
                    user.pk = person.pk
                    unfollowersProfiles.add(user)
                }
            }
            observableEmitter.onNext(unfollowersProfiles)

        }

    }

    fun setInstagram(instagram4Android: Instagram4Android) {
        instagram = instagram4Android
    }

    fun unfollow(user: UserProfile) {
        disposable.add(Completable.fromAction {
            instagram!!.sendRequest(InstagramUnfollowRequest(user.pk))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getUnfollowers() }
        )
    }

    fun onDestroyPresenter() {
        disposable.dispose()
    }

}