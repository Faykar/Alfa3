package com.bagicode.alfa3.admin.dashboard.data_product


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_product.adapter_product.AdminTimKecilAdapter
import com.bagicode.alfa3.admin.dashboard.data_product.updateproduct.UpdateTimKecilActivity
import com.bagicode.alfa3.user.home.tim.model.getTimKecil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_admin_tim_besar.*
import kotlinx.android.synthetic.main.activity_admin_tim_besar.iv_back
import kotlinx.android.synthetic.main.activity_admin_tim_kecil.*

class AdminTimKecilActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var data = ArrayList<getTimKecil>()
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tim_kecil)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Tim Kecil")

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
                    val timKecil = getdataSnapshot.getValue(getTimKecil::class.java)

                    val key = getdataSnapshot.key.toString()
                    val harga = timKecil?.harga
                    val stok = timKecil?.stok
                    val jenis = timKecil?.jenis
                    val desc = timKecil?.desc
                    val url = timKecil?.url
                    data.add(setData(key,harga!!,jenis!!,stok!!,desc!!,url!!))
                }
                if (data.isNotEmpty()){
                    rv_admin_tim_kecil.adapter = AdminTimKecilAdapter(data){
                        val intent = Intent(
                            applicationContext,
                            UpdateTimKecilActivity::class.java
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

    private fun setData(key: String, harga: Int, jenis : String, stok: Int, desc: String, url: String): getTimKecil {
        val data = getTimKecil(
            key,
            harga,
            jenis,
            desc,
            url,
            stok
        )
        return data
    }

}