package com.instagramliker.mobileapp.ui.unfollowers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instagramliker.mobileapp.R
import com.instagramliker.mobileapp.data.model.UserProfile
import kotlinx.android.synthetic.main.item_followers.view.*


class UnfollowersAdapter(
    private val users: List<UserProfile>,
    private val context: Context) :
    RecyclerView.Adapter<UnfollowersAdapter.ViewHolder>() {

    var onUnfollowClick: ((UserProfile) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_followers, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateWithUser(users[position])
    }


    override fun getItemCount(): Int {
        return users.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun updateWithUser(user: UserProfile) {


            Glide.with(context)
                .load(user.profilePicUrl)
                .into(itemView.image_userpic)

            itemView.text_username.text = user.userName

            itemView.btn_unfollow.setOnClickListener {
                onUnfollowClick?.invoke(user)
            }

        }


    }


}
