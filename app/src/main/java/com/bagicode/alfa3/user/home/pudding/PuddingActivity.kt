package com.bagicode.alfa3.user.home.pudding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.pudding.model.getPudding
import com.bagicode.alfa3.user.home.tim.PuddingAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pudding.*

class PuddingActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var context: Context

    private var dataList = ArrayList<getPudding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pudding)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Pudding")

        rv_item_pudding.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()

        iv_back.setOnClickListener {
            finish()
        }

    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){

                    val pudding = getdataSnapshot.getValue(getPudding::class.java)
                    val key = getdataSnapshot.key.toString()
                    val harga = pudding?.harga
                    val stok = pudding?.stok
                    val jenis = pudding?.jenis
                    val desc = pudding?.desc
                    val url = pudding?.url
                    dataList.add(setData(key, harga, stok, jenis, desc, url))
                }

                if (dataList.isNotEmpty()){
                    rv_item_pudding.adapter = PuddingAdapter(dataList){
                        val intent = Intent(
                            applicationContext,
                            DetailPuddingActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun setData(key: String, harga: Int?, stok: Int?, jenis: String?, desc: String?, url: String?): getPudding {
        val data = getPudding (
            key, harga, jenis, desc, url, stok
        )
        return data

    }


}