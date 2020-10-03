package com.bagicode.alfa3.admin.dashboard.data_transaction


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.PendingTransaksiAdapter
import com.bagicode.alfa3.admin.tab_layout.DetailAdapter.DetailTransaksiPendingAdapter
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var transRef: DatabaseReference
    lateinit var isiRef: DatabaseReference

    private var username : String = ""

    private var data = ArrayList<User>()
//    private var dataTrans = ArrayList<Transaksi>()
    private var isiTrans = ArrayList<isiTransaksi>()

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


                    Log.v("2222", "Data user"+data)
                    Log.v("2223", "Data user"+dataUser)

                }

                for (a in data.indices){
                    username = data[a].username.toString()
                    Log.v("bow","data user is "+username)


                    val keyTransaksi = transRef
                        .child(username)
                        .child("transaksi")
                        .push().key

                    transRef
//                        .child(username)
//                        .child("transaksi")
//                        .child(keyTransaksi.toString())
//                        .child("Pesanan")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                            isiTrans.clear()
                                for (getdataTransSnapshot in dataSnapshot.children){
                                    val isitransaksi = getdataTransSnapshot.getValue(isiTransaksi::class.java)

                                    val keyProduct = getdataTransSnapshot.key.toString()
                                    val desc = isitransaksi?.desc
                                    val harga = isitransaksi?.harga
                                    val jenis = isitransaksi?.jenis
                                    val url = isitransaksi?.url


                                    isiTrans.add(setData(keyProduct, harga!!, jenis!!, desc!!, url!!))
                                    Log.v("1311","Transaksi User == $isitransaksi")
                                }
                                if (isiTrans.isNotEmpty()){
                                    rv_transaction_user.adapter = DetailTransaksiPendingAdapter(isiTrans){

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


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TransactionActivity, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun setData(keyProduct: String,  harga: Int, jenis: String, desc: String, url: String): isiTransaksi {
        val data = isiTransaksi (
            keyProduct, harga, jenis, desc,url
        )
        return data

    }
}