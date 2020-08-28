package com.bagicode.alfa3.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bagicode.alfa3.R
import com.bagicode.alfa3.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfileActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences
    lateinit var user : User
    lateinit var updateNama : String
    lateinit var updateNomor : Number
    lateinit var updatePassword: String
    lateinit var showUsername : String


    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference



    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val data = intent.getParcelableExtra<User>("data")

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")
            .child("user")





        preferences = Preferences(applicationContext)

        val showNama = et_nama.setText(user.nama)
        val showNomor = et_nomor.setText(user.nomor)
        val showPassword = et_password.setText(user.password)
        val showUsername = preferences.getValues("user").toString()


        btn_update.setOnClickListener {
//            upNama = et_nama.text.toString()
//            upNomor = et_nomor.text.toString()
//            upPassword = et_password.text.toString()




//            updateUser(updateNama, updateNomor, updatePassword)


//            var map = mutableMapOf<String, Any>()
//        map["nama"] = updateNama
//        map["nomor"] = updateNomor
//        map["password"] = updatePassword
//
//       mFirebaseDatabase
//            .updateChildren(map)


        }
    }
    private fun updateUser(
        updateNama: String,
        updateNomor: Int,
        updatePassword: String
    ) {
        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user = User(updateNama, updateNomor.toString(), updatePassword)

                mFirebaseDatabase
                    .child(user.username!!)
                    .setValue(user)



                preferences.setValues("nama", updateNama)
                preferences.setValues("nomor", updateNomor.toString())
                preferences.setValues("password", updatePassword)



                finishAffinity()
                val intent = Intent(this@EditProfileActivity,
                    HomeActivity::class.java).putExtra("data", user)
                startActivity(intent)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfileActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })


    }




    }





