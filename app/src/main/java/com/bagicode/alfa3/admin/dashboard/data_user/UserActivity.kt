package com.bagicode.alfa3.admin.dashboard.data_user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_user.adapter_user.AdminUserAdapter
import com.bagicode.alfa3.admin.dashboard.data_user.update_user.UpdateUserActivity
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {


    lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<User>()
    private lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")


        rv_admin_user.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()

        iv_back.setOnClickListener {
            finish()
        }

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
             data.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    val user = getdataSnapshot.getValue(User::class.java)
                    val username = user?.username
                    val nama = user?.nama
                    val nomor = user?.nomor
                    val password = user?.password
                    val url = user?.url
                    data.add(setData(username!!, nama!!, nomor!!, password!!, url!!))
                    Log.v("333", "User ADALAH  $user")
                    if (data.isNotEmpty()){
                        rv_admin_user.adapter = AdminUserAdapter(data){
                            val intent = Intent(
                                applicationContext,
                                UpdateUserActivity::class.java
                            ).putExtra("User", it)
                            startActivity(intent)
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setData(username: String, nama: String, nomor: String, password: String, url: String): User {
        val data = User(
            username, nama, nomor, password, url

        )
        return data

    }


}