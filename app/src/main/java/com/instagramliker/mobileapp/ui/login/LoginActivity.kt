package com.instagramliker.mobileapp.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.instagramliker.mobileapp.R
import com.instagramliker.mobileapp.data.model.Instagram
import com.instagramliker.mobileapp.ui.unfollowers.activity.MainActivity
import com.instagramliker.mobileapp.util.ConnectionHelper
import com.instagramliker.mobileapp.util.Constants
import com.instagramliker.mobileapp.util.androidx.MvpAppCompatActivity
import com.instagramliker.mobileapp.util.showError
import dev.niekirk.com.instagram4android.Instagram4Android
import hachaton.codeninjas.com.util.PrefsUtil
import hachaton.codeninjas.com.util.PrefsUtil.set
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var prefs: SharedPreferences
    @InjectPresenter
    lateinit var presenter: LoginPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefs = PrefsUtil.defaultPrefs(this)


        if (prefs.getString(Constants.INSTAGRAM_NAME, "") != "") {
            input_password.visibility = View.GONE
            input_username.visibility = View.GONE
            btn_login.visibility = View.GONE
            checkIfNetworkAvailable()
        }
        btn_login.setOnClickListener {

            if (input_username.text.isEmpty() || input_password.text.isEmpty()) {
                showError("Заполните все поля!")
                return@setOnClickListener
            }
            if(!ConnectionHelper.isNetworkAvailable(this)) {
                showError("Проверьте соединение с интернетом!")
                return@setOnClickListener
            }
            presenter.onLoginBtnClicked(input_username.text.toString(), input_password.text.toString())
        }
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.GONE
    }

    override fun onLoginSuccess(instagram: Instagram4Android) {
        Instagram.setInstagram(instagram)
        prefs[Constants.INSTAGRAM_NAME] = instagram.username
        prefs[Constants.INSTAGRAM_PASSWORD] = instagram.password
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


    override fun onError(message: String) {
        showError(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }


    private fun checkIfNetworkAvailable() {
        if (ConnectionHelper.isNetworkAvailable(this)) {
            presenter.onLoginBtnClicked(
                prefs.getString(Constants.INSTAGRAM_NAME, "")!!,
                prefs.getString(Constants.INSTAGRAM_PASSWORD, "")!!
            )
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Подключение недоступно", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.parseColor("#FFFFFF"))
                .setAction("Повторить") {
                    checkIfNetworkAvailable()
                }
                .show()


        }
    }
}