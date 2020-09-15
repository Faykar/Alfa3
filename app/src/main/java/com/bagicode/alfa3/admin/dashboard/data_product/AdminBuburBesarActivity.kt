package com.bagicode.alfa3.admin.dashboard.data_product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_product.adapter_product.AdminBuburBesarAdapter
import com.bagicode.alfa3.admin.dashboard.data_product.updateproduct.UpdateBuburBesarActivity
import com.bagicode.alfa3.user.home.bubur.model.getBuburBesar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_bubur_besar.*

class AdminBuburBesarActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<getBuburBesar>()
    private lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_bubur_besar)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Bubur Besar")

        rv_admin_bubur_besar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                    data.add(setData(key, harga!!, stok!!, desc!!, url!!))
                    Log.v("Hehe","Check Key "+ getdataSnapshot.key)
                    Log.v("Hehe","Check Key "+ buburBesar?.harga)
                }

                if (data.isNotEmpty()) {
                    rv_admin_bubur_besar.adapter = AdminBuburBesarAdapter(data){
                        val intent = Intent(
                            applicationContext,
                            UpdateBuburBesarActivity::class.java
                        ).putExtra("data besar", it)
                        startActivity(intent)

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG)
                    .show()
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