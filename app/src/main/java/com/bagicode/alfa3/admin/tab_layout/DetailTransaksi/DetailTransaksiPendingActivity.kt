package com.bagicode.alfa3.admin.tab_layout.DetailTransaksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.tab_layout.DetailAdapter.DetailTransaksiPendingAdapter
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_transaksi_pending.*


class DetailTransaksiPendingActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
//    lateinit var isi: isiTransaksi
    private var isiData = ArrayList<isiTransaksi>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi_pending)

        val transaksi = intent.getParcelableExtra<Transaksi>("data")

        val keyProduct = transaksi.key

        Log.v("kuncinya", "key is "+transaksi.key)
        Log.v("usernamenya", "username is "+transaksi.username)


        mDatabase = FirebaseDatabase.getInstance().reference
            .child("User")
            .child(transaksi.username)
            .child("Transaksi")
            .child(keyProduct.toString())
            .child("Pesanan")


        rv_detail_pesanan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    isiData.clear()
                    for (getdataSnapshot in dataSnapshot.children){
                        val isi = getdataSnapshot.getValue(isiTransaksi::class.java)
                        val keyProduct = getdataSnapshot.key.toString()
                        val desc = isi?.desc
                        val jenis = isi?.jenis
                        val harga = isi?.harga
                        val url = isi?.url


                        isiData.add(isiTransaksiData(keyProduct,harga!!,jenis!!,desc!!,url!!))
                        isiData.add(isi)
                        Log.v("blableblo", "isi Transaksinya keluar ga?  $isi")
                        Log.v("blableblo", "Keynya keluar ga?  $keyProduct")


                    }
                    if (isiData.isNotEmpty()){
                        rv_detail_pesanan.adapter = DetailTransaksiPendingAdapter(isiData){

                        }
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DetailTransaksiPendingActivity, ""+error.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }


    private fun isiTransaksiData(
        key: String,
        harga: Int,
        jenis: String,
        desc: String,
        url: String
    ): isiTransaksi {
        val isiData = isiTransaksi(
            key, harga, jenis, desc,url
        )
        return isiData

    }
}