package com.bagicode.alfa3.admin.dashboard.data_product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_product.adapter_product.AdminTimBesarAdapter
import com.bagicode.alfa3.admin.dashboard.data_product.updateproduct.UpdateTimBesarActivity
import com.bagicode.alfa3.user.home.tim.model.getTimBesar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_tim_besar.*

class AdminTimBesarActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<getTimBesar>()
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tim_besar)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Tim Besar")

        rv_admin_tim_besar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                    val timBesar = getdataSnapshot.getValue(getTimBesar::class.java)

                    val key = getdataSnapshot.key.toString()
                    val harga = timBesar?.harga
                    val stok = timBesar?.stok
                    val desc = timBesar?.desc
                    val url = timBesar?.url
                    data.add(setData(key,harga!!,stok!!,desc!!,url!!))
                }
                if (data.isNotEmpty()){
                    rv_admin_tim_besar.adapter = AdminTimBesarAdapter(data){
                        val intent = Intent(
                            applicationContext,
                            UpdateTimBesarActivity::class.java
                        ).putExtra("data besar", it)
                        startActivity(intent)

                    }

                }            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    private fun setData(key: String, harga: Int, stok: Int, desc: String, url: String): getTimBesar {
        val data = getTimBesar(
            key,
            harga,
            stok,
            desc,
            url
        )
        return data
    }

}