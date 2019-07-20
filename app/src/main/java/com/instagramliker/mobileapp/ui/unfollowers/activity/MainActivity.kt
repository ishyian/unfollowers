package com.instagramliker.mobileapp.ui.unfollowers.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.instagramliker.mobileapp.R
import com.instagramliker.mobileapp.data.model.Instagram
import com.instagramliker.mobileapp.ui.login.LoginActivity
import com.instagramliker.mobileapp.ui.unfollowers.followers.UnfollowersFragment
import com.instagramliker.mobileapp.util.Constants
import hachaton.codeninjas.com.util.PrefsUtil
import hachaton.codeninjas.com.util.PrefsUtil.set
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        prefs = PrefsUtil.defaultPrefs(this)
        setFragment()
        setNavDrawer()
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, UnfollowersFragment()).commit()
    }


    private fun setNavDrawer() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        val instagram = Instagram.instagram4Android
        if (instagram != null && instagram.isLoggedIn) {
            val navHeader = navView.getHeaderView(0)
            val username = navHeader.findViewById<TextView>(R.id.text_username)
            username.text = instagram.username
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun logOut(){
        prefs[Constants.INSTAGRAM_NAME] = ""
        prefs[Constants.INSTAGRAM_PASSWORD] = ""
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> logOut()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }




}
