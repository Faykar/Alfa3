package com.bagicode.alfa3.admin.dashboard.data_product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_product.adapter_product.AdminPuddingAdapter
import com.bagicode.alfa3.admin.dashboard.data_product.updateproduct.UpdatePuddingActivity
import com.bagicode.alfa3.user.home.pudding.model.getPudding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_pudding.*

class AdminPuddingActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<getPudding>()
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pudding)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Pudding")

        rv_admin_pudding.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
                    val pudding = getdataSnapshot.getValue(getPudding::class.java)
                    val key = getdataSnapshot.key.toString()
                    val harga = pudding?.harga
                    val jenis = pudding?.jenis
                    val stok = pudding?.stok
                    val desc = pudding?.desc
                    val url = pudding?.url
                    data.add(setData(key,harga!!,stok!!,desc!!,jenis!!,url!!))

                }
                if (data.isNotEmpty()){
                    rv_admin_pudding.adapter = AdminPuddingAdapter(data){
                        val intent = Intent(applicationContext,
                        UpdatePuddingActivity::class.java)
                            .putExtra("data besar", it)
                        startActivity(intent)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }


        })

    }
    private fun setData(key: String,harga: Int,stok: Int,desc: String,jenis: String,url: String
    ) : getPudding {
        val data = getPudding (
            key,
            harga,
            stok,
            desc,
            jenis,
            url
        )
        return data
    }


}