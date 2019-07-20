package com.instagramliker.mobileapp.ui.unfollowers.followers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.instagramliker.mobileapp.R
import com.instagramliker.mobileapp.data.model.Instagram
import com.instagramliker.mobileapp.data.model.UserProfile
import com.instagramliker.mobileapp.ui.unfollowers.adapter.UnfollowersAdapter
import com.instagramliker.mobileapp.util.androidx.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_followers.*


class UnfollowersFragment : MvpAppCompatFragment(), UnfollowersView {

    @InjectPresenter
    lateinit var presenter: UnfollowersPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_unfollowers.layoutManager = LinearLayoutManager(context!!)
        presenter.setInstagram(Instagram.instagram4Android!!)
        presenter.getUnfollowers()

    }

    override fun updateUnfollowers(unfollowers: List<UserProfile>) {
        hideProgress()
        val adapter = UnfollowersAdapter(unfollowers, context!!)
        adapter.onUnfollowClick = { user ->
            presenter.unfollow(user)
        }
        rv_unfollowers.adapter = adapter

    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE

    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyPresenter()
    }
}
