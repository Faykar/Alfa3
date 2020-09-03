package com.bagicode.alfa3.Admin.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bagicode.alfa3.Admin.HomeAdminActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginAdminActivity : AppCompatActivity() {
    lateinit var iUsername :String
    lateinit var iPassword :String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_admin)

        mDatabase = FirebaseDatabase.getInstance()
            .getReference("User")
            .child("Admin")
        preferences = Preferences(this)


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



    }
    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val admin = dataSnapshot.getValue(Admin::class.java)
                if (admin == null) {
                    Toast.makeText(this@LoginAdminActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()

                } else {
                    if (admin.password.equals(iPassword)){
                        Toast.makeText(this@LoginAdminActivity, "Selamat Datang", Toast.LENGTH_SHORT).show()

                        preferences.setValues("username", admin.username.toString())
                        preferences.setValues("password", admin.password.toString())

                        finishAffinity()

                        val intent = Intent(this@LoginAdminActivity,
                            HomeAdminActivity::class.java).putExtra("user", admin)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@LoginAdminActivity, "Password Anda Salah", Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LoginAdminActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}