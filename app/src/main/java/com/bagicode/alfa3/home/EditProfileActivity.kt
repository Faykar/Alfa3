package com.bagicode.alfa3.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*



class EditProfileActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences
//    lateinit var user : User

    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference



    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")




        preferences = Preferences(applicationContext)

        var updateNama = et_nama.setText(preferences.getValues("nama")).toString()
        var updateNomor = et_nomor.setText(preferences.getValues("nomor")).toString()
        var updatePassword = et_password.setText(preferences.getValues("password")).toString()



        btn_update.setOnClickListener {
//            upNama = et_nama.text.toString()
//            upNomor = et_nomor.text.toString()
//            upPassword = et_password.text.toString()





//            var map = mutableMapOf<String, Any>()
//        map["nama"] = updateNama
//        map["nomor"] = updateNomor
//        map["password"] = updatePassword
//
//       mFirebaseDatabase
//            .updateChildren(map)
        }

    }


}