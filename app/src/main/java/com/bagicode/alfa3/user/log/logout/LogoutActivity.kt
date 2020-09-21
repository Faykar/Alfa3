package com.bagicode.alfa3.user.log.logout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.user.log.login.LoginActivity
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LogoutActivity : AppCompatActivity() {
   lateinit var preferences: Preferences
   lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = Preferences(applicationContext)

        val pref = preferences.sharedPref
        val edit = pref.edit()
        edit.remove("user")
        edit.remove("status")
        edit.commit()

        finishAffinity()
        Toast.makeText(this@LogoutActivity, "Berhasil Logout", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@LogoutActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)





    }
}