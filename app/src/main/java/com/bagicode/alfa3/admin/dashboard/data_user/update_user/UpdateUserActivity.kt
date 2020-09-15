package com.bagicode.alfa3.admin.dashboard.data_user.update_user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.DashboardActivity
import com.bagicode.alfa3.user.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_user.*
import kotlinx.android.synthetic.main.activity_update_user.btn_delete
import kotlinx.android.synthetic.main.activity_update_user.btn_update
import kotlinx.android.synthetic.main.activity_update_user.iv_poster_image

class UpdateUserActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var preference: Preferences

    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference

    lateinit var updateNama : String
    lateinit var updateNomor : String
    lateinit var updatePassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        val data = intent.getParcelableExtra<User>("User")
        preference = Preferences(applicationContext)

        //Mengambil data dari Recycler View milik User
        tvUsername.text = data.username

        var username = data.username.toString()

        val Nama = data.nama
        val Nomor = data.nomor
        val Password = data.password

        et_nama.setText(Nama)
        et_nomor.setText(Nomor)
        et_password.setText(Password)

        Glide.with(this)
            .load(data.url)
            .into(iv_poster_image)

        ic_back.setOnClickListener {
            finish()
        }

        btn_delete.setOnClickListener {
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance.getReference("User")
                .child(username)

            mFirebaseDatabase.removeValue()

            Toast.makeText(this@UpdateUserActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT)
                .show()

            val intent = Intent(this@UpdateUserActivity,
                DashboardActivity::class.java)
            startActivity(intent)

        }

        btn_update.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.show()
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance.getReference("User")
                .child(username)

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

                val hashMap: HashMap<String, String> = HashMap()
                hashMap["nama"] = updateNama
                hashMap["nomor"] = updateNomor
                hashMap["password"] = updatePassword

                mFirebaseDatabase
                    .updateChildren(hashMap as Map<String, String>)

                    .addOnSuccessListener {
                        progressDialog.onContentChanged()
                        progressDialog.setTitle("Data Berhasil ditambahkan")
                        progressDialog.show()

                        finishAffinity()
                        val intent = Intent (this@UpdateUserActivity,
                            DashboardActivity::class.java)

                        startActivity(intent)

                    }

                    .addOnFailureListener{
                        progressDialog.setTitle("Data tidak boleh kosong")
                        progressDialog.show()


                    }



            }


        }


    }
}