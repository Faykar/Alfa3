package com.bagicode.alfa3.home


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity

import com.bagicode.alfa3.R
import com.bagicode.alfa3.log.login.User
import com.bagicode.alfa3.log.logout.LogoutActivity
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    lateinit var preferences: Preferences
    lateinit var user : User

    lateinit var mFirebaseDatabase : DatabaseReference
    lateinit var mFirebaseInstance : FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")




        preferences = Preferences(context!!.applicationContext)

        tv_user.text = preferences.getValues("nama")
        tv_nomor.text = preferences.getValues("nomor")

        val takeImage = preferences.getValues("url")


        Glide.with(this)
            .load(takeImage)
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        tvEdit.setOnClickListener{


            startActivity(Intent(activity, EditProfileActivity::class.java))

        }

        tvLogout.setOnClickListener {
            startActivity(Intent(activity, LogoutActivity::class.java))
        }



    }


}
