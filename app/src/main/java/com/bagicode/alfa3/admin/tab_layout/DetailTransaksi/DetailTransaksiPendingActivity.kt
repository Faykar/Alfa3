package com.bagicode.alfa3.admin.tab_layout.DetailTransaksi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_transaction.Pesanan.Pesanan
import com.bagicode.alfa3.admin.tab_layout.DetailAdapter.DetailTransaksiPendingAdapter
import com.bagicode.alfa3.admin.tab_layout.HomeAdminActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_transaksi_pending.*


class DetailTransaksiPendingActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var dataPesanan = ArrayList<Pesanan>()


//    val dataPesanan = intent.getSerializableExtra("Pesanan") as ArrayList<isiTransaksi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi_pending)

        mDatabase = FirebaseDatabase.getInstance().reference
            .child("User")



        rv_detail_pesanan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getData()


        val transaksi = intent.getParcelableExtra<Transaksi>("data")!!

        val key = transaksi.key.toString()
        val username = transaksi.username

        btn_terima.setOnClickListener {
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["status"] = "Progress"

            mDatabase
                .child(username)
                .child("transaksi")
                .child(key)
                .updateChildren(hashMap as Map<String,String>)

            finish()
            startActivity(Intent(this, HomeAdminActivity::class.java))

        }

        btn_tolak.setOnClickListener {
            mDatabase.child(username)
                .child("transaksi")
                .child(key)
                .removeValue()

            finish()
            startActivity(Intent(this, HomeAdminActivity::class.java))
        }



    }

    private fun getData() {
        val transaksi = intent.getParcelableExtra<Transaksi>("data")!!

        val key = transaksi.key.toString()
        val username = transaksi.username


        Log.v("0192","Key Transaksinya "+key)
        Log.v("0193","Username Transaksinya "+username)

       mDatabase
                .child(username)
                .child("transaksi")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            dataTrans.clear()
                        for (getdataTransSnapshot in dataSnapshot.children) {
                          for (getchild in getdataTransSnapshot.children) {
                                val getKey = getchild.key.toString()
                                val getRef = getchild.ref

                                Log.v("1211", "Key Childnya $getKey")
                                Log.v("1212", "Ref Childnya $getRef")

                                for (getPesanan in getchild.children) {
                                    val pesanan = getPesanan.getValue(Pesanan::class.java)!!
                                    val keyPesanan = getPesanan.key.toString()
                                    val harga = pesanan.harga
                                    val jenis = pesanan.jenis
                                    val desc = pesanan.desc
                                    val url = pesanan.url


                                    dataPesanan.add(pesanan)
//                                dataPesanan.add(getPesanan(keyPesanan,harga!!,jenis!!,desc!!,url!!))
                                    Log.v("1311", "Pesanan User ==" + dataPesanan)
                                    Log.v("1310", "Value Pesanan == "+pesanan)
                                }


                            }
                        }
                        if (dataPesanan.isNotEmpty()){
                            rv_detail_pesanan.adapter = DetailTransaksiPendingAdapter(dataPesanan){

                            }
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@DetailTransaksiPendingActivity, ""+error.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                })

    }


    private fun getPesanan(
        key: String,
        harga: Int,
        jenis: String,
        desc: String,
        url: String
    ): Pesanan {
        return Pesanan(
            key, harga, jenis, desc,url
        )

    }
}