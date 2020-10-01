package com.bagicode.alfa3.admin.dashboard.data_transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.TransaksiAdapter
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var transRef: DatabaseReference

    private var username : String = ""

    lateinit var user : User
    private var data = ArrayList<User>()
    private var dataTrans = ArrayList<Transaksi>()
    private var isiTrans = ArrayList<isiTransaksi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        transRef = FirebaseDatabase.getInstance().getReference("User")



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


                    Log.v("2222", "Data user"+data)
                    Log.v("2223", "Data user"+dataUser)

                }

                for (a in data.indices){
                    username = data[a].username.toString()
//                    Log.v("bow","data user is "+username)
                    transRef
                        .child(username)
                        .child("transaksi")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            dataTrans.clear()
                                for (getdataTransSnapshot in dataSnapshot.children){
                                    val transaksi = getdataTransSnapshot.getValue(Transaksi::class.java)

                                    val keyProduct = getdataTransSnapshot.key.toString()
                                    val nama = transaksi?.nama
                                    val nomor = transaksi?.nomor
                                    val bukti = transaksi?.bukti
                                    val hargaTotal = transaksi?.hargaTotal


                                    dataTrans.add(transaksi!!)
                                    dataTrans.add(setData(keyProduct, nama!!, nomor!!, hargaTotal!!, bukti!!))
                                    Log.v("1311","Transaksi User == $transaksi")




                                }
                                if (dataTrans.isNotEmpty()){
                                    rv_transaction_user.adapter = TransaksiAdapter(dataTrans){

                                    }
                                }


                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(this@TransactionActivity, ""+error.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        })
                }

                Log.v("bow","data user is "+username)







            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TransactionActivity, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setData(keyProduct: String, nama: String, nomor: String, hargaTotal: Int, bukti: String): Transaksi {
        val data = Transaksi (
            keyProduct, nama, nomor, hargaTotal,bukti
        )
        return data

    }
}