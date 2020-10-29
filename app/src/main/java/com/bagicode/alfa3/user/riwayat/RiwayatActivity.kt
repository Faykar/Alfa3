package com.bagicode.alfa3.user.riwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.cart.CartAdapter
import com.bagicode.alfa3.user.home.cart.getCart
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.bagicode.alfa3.user.home.pudding.model.addPudding
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_riwayat.*

class RiwayatActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    private var dataTrans = ArrayList<Transaksi>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        preferences = Preferences(this)

        mDatabase =  FirebaseDatabase.getInstance().getReference("User")
            .child(preferences.getValues("user").toString())

        rv_history.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getData()
    }

    private fun getData(){
        mDatabase.child("transaksi").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataTrans.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val transaksi = getdataSnapshot.getValue(Transaksi::class.java)!!
                    Log.v("blablabla", "Data cartnya apa nih   " + transaksi)

                    dataTrans.add(transaksi)

                }

                if (dataTrans.isNotEmpty()) {
                    rv_history.adapter = RiwayatAdapter(dataTrans) {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RiwayatActivity, "" + error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}

