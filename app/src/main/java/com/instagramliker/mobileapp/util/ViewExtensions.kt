package com.instagramliker.mobileapp.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun AppCompatActivity.showError(message: String){
    Toasty.error(this, message, Toasty.LENGTH_SHORT).show()
}

fun Fragment.showError(message: String){
    Toasty.error(context!!.applicationContext, message, Toasty.LENGTH_SHORT).show()
}

fun Fragment.showSuccess(message: String){
    Toasty.success(this.context!!, message, Toasty.LENGTH_SHORT).show()
}

fun Context.showError(message: String){
    Toasty.error(this, message, Toasty.LENGTH_SHORT).show()
}

fun Context.showSuccess(message: String){
    Toasty.success(this, message, Toasty.LENGTH_SHORT).show()
}