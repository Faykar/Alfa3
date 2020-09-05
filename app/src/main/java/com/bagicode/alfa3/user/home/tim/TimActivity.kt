package com.bagicode.alfa3.user.home.tim

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.tim.model.getTimBesar
import com.bagicode.alfa3.user.home.tim.model.getTimKecil
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tim.*
import kotlinx.android.synthetic.main.activity_tim.iv_back


class TimActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var context: Context
    lateinit var mDatabaseKecil: DatabaseReference

    private var dataList = ArrayList<getTimBesar>()
    private var dataListKecil = ArrayList<getTimKecil>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tim)

        mDatabase = FirebaseDatabase.getInstance().reference.child("Tim Besar")
        mDatabaseKecil = FirebaseDatabase.getInstance().reference.child("Tim Kecil")

        rv_item_tim_besar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_item_tim_kecil.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()


        iv_back.setOnClickListener{
            finish()
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {

                    val timBesar = getdataSnapshot.getValue(getTimBesar::class.java)
                    dataList.add(timBesar!!)
                }

                if (dataList.isNotEmpty()){
                    rv_item_tim_besar.adapter = TimBesarAdapter(dataList){
                        val intent = Intent(
                            applicationContext,
                            DetailTimBesarActivity::class.java
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
                    val timKecil = getdataSnapshot.getValue(getTimKecil::class.java)
                    dataListKecil.add(timKecil!!)
                }

                if (dataListKecil.isNotEmpty()){
                    rv_item_tim_kecil.adapter = TimKecilAdapter(dataListKecil){
                        val intent = Intent(
                            applicationContext,
                            DetailTimKecilActivity::class.java
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

}