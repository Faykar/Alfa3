package com.bagicode.alfa3.admin.dashboard.product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.adapter_product.AdminBuburKecilAdapter
import com.bagicode.alfa3.admin.dashboard.updateproduct.UpdateBuburKecilActivity
import com.bagicode.alfa3.user.home.bubur.model.getBuburKecil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_bubur_kecil.*

class AdminBuburKecilActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<getBuburKecil>()
    private lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_bubur_kecil)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Bubur Kecil")

        rv_admin_bubur_kecil.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                    val buburKecil = getdataSnapshot.getValue(getBuburKecil::class.java)
                    val key = getdataSnapshot.key.toString()
                    val harga = buburKecil?.harga
                    val stok = buburKecil?.stok
                    val desc = buburKecil?.desc
                    val url = buburKecil?.url
                    data.add(setData(key, harga!!, stok!!, desc!!, url!!))
                }
                if (data.isNotEmpty()){
                    rv_admin_bubur_kecil.adapter = AdminBuburKecilAdapter(data){
                        val intent = Intent(
                            applicationContext,
                            UpdateBuburKecilActivity::class.java
                        ).putExtra("data besar", it)
                        startActivity(intent)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" +error.message, Toast.LENGTH_LONG)
                    .show()
            }

        })

    }
    private fun setData(key: String, harga: Int, stok: Int, desc: String, url: String): getBuburKecil {
        val data = getBuburKecil(
            key,
            harga,
            stok,
            desc,
            url
        )

        return data

    }





}