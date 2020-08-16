package com.bagicode.alfa3.log.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bagicode.alfa3.R
import com.bagicode.alfa3.log.register.RegisterPhotoscreenActivity
import com.google.firebase.database.*
import com.bagicode.alfa3.utils.Preferences
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sNomor: String


    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        iv_back.setOnClickListener {
            finish()
        }

        btn_daftar.setOnClickListener{
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sNomor = et_nomor.text.toString()

            if (sUsername.equals("")){
                et_username.error = "Silahkan isi Username"
                et_username.requestFocus()
            } else if (sPassword.equals("")){
                et_password.error = "Silahkan isi Password"
                et_password.requestFocus()
            } else if (sNama.equals("")){
                et_nama.error = "Silahkan isi Nama"
                et_nama.requestFocus()
            } else if (sNomor.equals("")){
                et_nomor.error = "Silahkan isi Nomor"
                et_nomor.requestFocus()
            } else {

                var statusUsername = sUsername.indexOf(".")
                if (statusUsername >= 0){
                    et_username.error = "Silahkan tulis Username anda tanpa ."
                    et_username.requestFocus()
                } else {
                    saveUser(sUsername, sPassword, sNama, sNomor)
                }
            }
        }
    }
//
    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sNomor: String){
        val user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.nomor = sNomor

        if (sUsername != null){
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(iUsername: String, data: User){
        mFirebaseDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){

            val user = dataSnapshot.getValue(User::class.java)
            if (user == null){
                mFirebaseDatabase.child(iUsername).setValue(data)

                preferences.setValues("nama", data.nama.toString())
                preferences.setValues("user", data.username.toString())
                preferences.setValues("url", "")
                preferences.setValues("nomor", data.nomor.toString())
                preferences.setValues("status", "")

                val intent = Intent(this@RegisterActivity,
                    RegisterPhotoscreenActivity::class.java).putExtra("nama", data.nama)
                startActivity(intent)
            } else {
                Toast.makeText(this@RegisterActivity, "Berhasil", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError){
                Toast.makeText(this@RegisterActivity, ""+error.message, Toast.LENGTH_LONG).show()
             }
        })
    }
}