package com.bagicode.alfa3.user.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.et_nama
import kotlinx.android.synthetic.main.activity_edit_profile.et_nomor
import kotlinx.android.synthetic.main.activity_edit_profile.et_password


class EditProfileActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences
    lateinit var updateNama : String
    lateinit var updateNomor : String
    lateinit var updatePassword: String
//    lateinit var showUsername : String


    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference

    @SuppressLint("ShowToast")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
//        user = intent.getParcelableExtra("data")

        preferences = Preferences(applicationContext)

        val showNama = preferences.getValues("nama").toString()
        val showNomor = preferences.getValues("nomor").toString()
        val showPassword = preferences.getValues("password").toString()
        // val showUsername = preferences.getValues("user").toString()
        et_nama.setText(showNama)
        et_nomor.setText(showNomor)
        et_password.setText(showPassword)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
            .child(preferences.getValues("user")!!)



        btn_update.setOnClickListener {

            updateNama = et_nama.text.toString()
            updateNomor = et_nomor.text.toString()
            updatePassword = et_password.text.toString()


            // Data cannot Empty
            if (updateNama.equals("")){
                et_nama.error = "Masukkan Nama"
                et_nama.requestFocus()
            } else if (updateNomor.equals("")){
                et_nomor.error = "Masukkan Nomor"
                et_nomor.requestFocus()
            } else if (updatePassword.equals("")){
                et_password.error = "Masukkan Password"
                et_password.requestFocus()
            } else {

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Menambahkan Data...")
                progressDialog.show()

                val hashMap: HashMap<String, String> = HashMap()
                hashMap["nama"] = updateNama
                hashMap["nomor"] = updateNomor
                hashMap["password"] = updatePassword

                mFirebaseDatabase
                    .updateChildren(hashMap as Map<String, Any>)

                    .addOnSuccessListener {
                        progressDialog.onContentChanged()
                        progressDialog.setTitle("Data Berhasil ditambahkan")
                        progressDialog.show()

                        finishAffinity()
                        val intent = Intent (this@EditProfileActivity,
                            HomeActivity::class.java)

                        startActivity(intent)

                    }

                    .addOnFailureListener{
                        progressDialog.setTitle("Data tidak boleh kosong")
                        progressDialog.show()


                    }

                Log.v("fayfay4", "Database is "+hashMap)

            }

            Log.v("fayfay1", "User is $updateNama")
            Log.v("fayfay2", "User is $updateNomor")
            Log.v("fayfay3", "User is $updatePassword")

        }

        }
    }










