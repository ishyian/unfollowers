package com.instagramliker.mobileapp.ui.unfollowers.followers

import com.arellomobile.mvp.MvpView
import com.instagramliker.mobileapp.base.BaseView
import com.instagramliker.mobileapp.data.model.UserProfile

interface UnfollowersView: MvpView, BaseView {

    fun updateUnfollowers(unfollowers: List<UserProfile>)

}