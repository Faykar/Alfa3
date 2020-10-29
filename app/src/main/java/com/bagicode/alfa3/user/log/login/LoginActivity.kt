package com.bagicode.alfa3.user.log.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.admin.sign.LoginAdminActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.HomeActivity
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var iUsername :String
    lateinit var iPassword :String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences
    private var directSecret = false
    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        icon.setOnClickListener {
            if (directSecret) {
                return@setOnClickListener
            }

            this.directSecret = true

            val intent = Intent(this@LoginActivity,
                LoginAdminActivity::class.java)
            startActivity(intent)

            Handler().postDelayed(Runnable {
                directSecret = false
            }, 3000)

        }

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValues("home", "1")
        if(preferences.getValues("status").equals("1")){
            finishAffinity()

            val intent = Intent(this@LoginActivity,
            HomeActivity::class.java)
            startActivity(intent)

        }

        btn_login.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if (iUsername.equals("")) {
                et_username.error = "Silahkan tulis Username Anda"
                et_username.requestFocus()
            } else if (iPassword.equals("")) {
                et_password.error = "Silahkan tulis Password Anda"
                et_password.requestFocus()
            } else {

                var statusUsername = iUsername.indexOf(".")
                if (statusUsername >= 0) {
                    et_username.error = "Silahkan tulis Username Anda tanpa."
                    et_username.requestFocus()
                } else {
                    pushLogin(iUsername, iPassword)
                }
            }
        }
        btn_regis.setOnClickListener{
            val intent = Intent(this@LoginActivity,
                RegisterActivity::class.java)
            startActivity(intent)
        }
        // Panel Admin

    }
    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    val progressDialog = ProgressDialog(this@LoginActivity)
                    progressDialog.setTitle("Tidak menemukan user")
                    progressDialog.show()


                } else {
                    if (user.password.equals(iPassword)){
                        Toast.makeText(this@LoginActivity, "Selamat Datang", Toast.LENGTH_SHORT).show()

                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("user", user.username.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("password", user.password.toString())
                        preferences.setValues("nomor", user.nomor.toString())
                        preferences.setValues("status", "1")

                        finishAffinity()

                        val intent = Intent(this@LoginActivity,
                            HomeActivity::class.java)
                            .putExtra("user", user)
                        startActivity(intent)

                    } else {
                        val progressDialog = ProgressDialog(this@LoginActivity)
                        progressDialog.setTitle("Password Salah")
                        progressDialog.show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    }



