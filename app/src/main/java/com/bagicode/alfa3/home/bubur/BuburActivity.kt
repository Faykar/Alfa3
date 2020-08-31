package com.bagicode.alfa3.home.bubur

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.home.bubur.model.getBuburKecil

import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_bubur.*
import kotlinx.android.synthetic.main.activity_bubur.iv_back
import kotlinx.android.synthetic.main.fragment_menu.*


class BuburActivity : AppCompatActivity() {


    //    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference
    private lateinit var context : Context
    lateinit var mDatabaseKecil: DatabaseReference

    private var data = ArrayList<getBuburBesar>()
    private var dataListKecil = ArrayList<getBuburKecil>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubur)

//        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Bubur Besar")
        mDatabaseKecil = FirebaseDatabase.getInstance().reference.child("Bubur Kecil")


        rv_item_bubur_besar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_item_bubur_kecil.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()


        iv_back.setOnClickListener{
            finish()
        }
    }


    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (getdataSnapshot in dataSnapshot.children) {

                    val buburBesar = getdataSnapshot.getValue(getBuburBesar::class.java)
                    val key = getdataSnapshot.key.toString()
                    val harga = buburBesar?.harga
                    val stok = buburBesar?.stok
                    val desc = buburBesar?.desc
                    val url = buburBesar?.url
                    data.add(setData(key,harga!!,stok!!,desc!!,url!!))
//                    Log.v("Hehe","Check Key "+ getdataSnapshot.key)
//                    Log.v("Hehe","Check Key "+ buburBesar?.harga)
                }

                if (data.isNotEmpty()){
                    rv_item_bubur_besar.adapter = BuburBesarAdapter(data){
                        val intent = Intent(
                            applicationContext,
                            DetailBuburBesarActivity::class.java
                        ).putExtra("data besar", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
        mDatabaseKecil.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataListKecil.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val buburKecil = getdataSnapshot.getValue(getBuburKecil::class.java)
                    dataListKecil.add(buburKecil!!)
                }

                if (dataListKecil.isNotEmpty()){
                    rv_item_bubur_kecil.adapter = BuburKecilAdapter(dataListKecil){
                        val intent = Intent(
                            applicationContext,
                            DetailBuburKecilActivity::class.java
                        ).putExtra("data kecil", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setData(key: String,harga: Int,stok: Int,desc: String, url: String): getBuburBesar {
        val data = getBuburBesar(
            key,
            harga,
            stok,
            desc,
            url
        )
        return data
    }


}








