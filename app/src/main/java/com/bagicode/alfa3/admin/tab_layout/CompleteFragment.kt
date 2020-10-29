package com.bagicode.alfa3.admin.tab_layout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_transaction.Pesanan.Pesanan
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.CompleteTransaksiAdapter
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.PendingTransaksiAdapter
import com.bagicode.alfa3.admin.tab_layout.DetailTransaksi.DetailTransaksiPendingActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_complete.*
import kotlinx.android.synthetic.main.fragment_pending.*


/**
 * A simple [Fragment] subclass.
 * Use the [CompleteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompleteFragment : Fragment() {

    lateinit var mDatabase: DatabaseReference
    lateinit var transRef : DatabaseReference


    private var data = ArrayList<User>()
    private var dataTrans = ArrayList<Transaksi>()
    private var dataPesanan = ArrayList<Pesanan>()

    private var username : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complete, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        transRef = FirebaseDatabase.getInstance().getReference("User")

        rv_transaction_complete.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })

        for (a in data.indices){
            username = data[a].username.toString()
            transRef
                .child(username)
                .child("transaksi")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (getdataTransSnapshot in dataSnapshot.children) {
                            val transaksi = getdataTransSnapshot.getValue(Transaksi::class.java)

                            dataTrans.add(transaksi!!)

                            Log.v("1312", "Array Transaksi" + dataTrans)

                            for (getchild in getdataTransSnapshot.children) {
                                val getKey = getchild.key.toString()
                                val getRef = getchild.ref

                                Log.v("1211", "Key Childnya $getKey")
                                Log.v("1212", "Ref Childnya $getRef")

                                for (getPesanan in getchild.children) {
                                    val pesanan = getPesanan.getValue(Pesanan::class.java)!!


                                    dataPesanan.add(pesanan)
                                    Log.v("1311", "Pesanan User ==" + dataPesanan)
                                    Log.v("1310", "Value Pesanan == "+pesanan)
                                }
                                if (dataPesanan.equals("Pending")){

                                }


                            }
                        }
                        if (dataTrans.isNotEmpty()){
                            rv_transaction_complete.adapter = CompleteTransaksiAdapter(dataTrans){

                            }
                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                })
        }
    }


}