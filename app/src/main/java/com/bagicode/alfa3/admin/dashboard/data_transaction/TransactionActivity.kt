package com.bagicode.alfa3.admin.dashboard.data_transaction


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_transaction.Pesanan.Pesanan
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.PendingTransaksiAdapter
import com.bagicode.alfa3.admin.tab_layout.DetailTransaksi.DetailTransaksiPendingActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var transRef: DatabaseReference
    lateinit var isiRef: DatabaseReference

    private var username : String = ""

    private var data = ArrayList<User>()
    private var dataTrans = ArrayList<Transaksi>()
    private var dataPesanan = ArrayList<Pesanan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        transRef = FirebaseDatabase.getInstance().getReference("Pesanan")
        isiRef = FirebaseDatabase.getInstance().getReference("User")

//        Log.v("user", "Dapatkah usernya $user")
        rv_transaction_user.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        getData()
    }
    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val dataUser = getdataSnapshot.getValue(User::class.java)

                    data.add(dataUser!!)

                    Log.v("2222", "Data user$data")
                    Log.v("2223", "Data user$dataUser")

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TransactionActivity, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })

        for (a in data.indices){
            username = data[a].username.toString()
//                    Log.v("bow","data user is "+username)
            transRef
                .child(username)
                .child("transaksi")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            dataTrans.clear()
                        for (getdataTransSnapshot in dataSnapshot.children) {
                            val transaksi = getdataTransSnapshot.getValue(Transaksi::class.java)
//                            val keyProduct = getdataTransSnapshot.key.toString()
//                            val nama = transaksi?.nama
//                            val nomor = transaksi?.nomor
//                            val bukti = transaksi?.bukti
//                            val hargaTotal = transaksi?.hargaTotal
//                            val username = transaksi?.username
//                            val status = transaksi?.status

                            dataTrans.add(transaksi!!)

//                            dataTrans.add(setData(keyProduct,username!!, nama!!,
//                                nomor!!, hargaTotal!!, bukti!!,status!!))
                            Log.v("1312", "Array Transaksi" + dataTrans)

                            for (getchild in getdataTransSnapshot.children) {
                                val getKey = getchild.key.toString()
                                val getRef = getchild.ref

                                Log.v("1211", "Key Childnya $getKey")
                                Log.v("1212", "Ref Childnya $getRef")

                                for (getPesanan in getchild.children) {
                                    val pesanan = getPesanan.getValue(Pesanan::class.java)!!
//                                    val keyPesanan = getPesanan.key.toString()
//                                    val harga = pesanan.harga
//                                    val jenis = pesanan.jenis
//                                    val desc = pesanan.desc
//                                    val url = pesanan.url


                                    dataPesanan.add(pesanan)
//                                dataPesanan.add(getPesanan(keyPesanan,harga!!,jenis!!,desc!!,url!!))
                                    Log.v("1311", "Pesanan User ==" + dataPesanan)
                                    Log.v("1310", "Value Pesanan == "+pesanan)
                                }


                            }
                        }
                        if (dataTrans.isNotEmpty()){
                            rv_transaction_user.adapter = PendingTransaksiAdapter(dataTrans){
                                val intent = Intent(this@TransactionActivity,
                                    DetailTransaksiPendingActivity::class.java)
                                    .putExtra("data", it)
                                    .putExtra("pesanan", dataPesanan)
                                startActivity(intent)

                            }
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@TransactionActivity, ""+error.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }
}