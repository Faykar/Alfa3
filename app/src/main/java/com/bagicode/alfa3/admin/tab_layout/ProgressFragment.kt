package com.bagicode.alfa3.admin.tab_layout

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
import com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi.ProgressTransaksiAdapter
import com.bagicode.alfa3.admin.tab_layout.DetailTransaksi.DetailTransaksiProgressActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.bagicode.alfa3.user.log.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_pending.*
import kotlinx.android.synthetic.main.fragment_progress.*


/**
 * A simple [Fragment] subclass.
 * Use the [ProgressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProgressFragment : Fragment() {


    lateinit var mDatabase: DatabaseReference
    lateinit var transRef : DatabaseReference
    lateinit var user: User
    private var username : String = ""

    private var data = ArrayList<User>()

    private var dataTrans = ArrayList<Transaksi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_progress, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        transRef = FirebaseDatabase.getInstance().getReference("User")


        rv_transaction_progress.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
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

                for (a in data.indices){
                    username = data[a].username.toString()
                    transRef
                        .child(username)
                        .child("transaksi")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            dataTrans.clear()
                                for (getdataTransSnapshot in dataSnapshot.children){
                                    val transaksi = getdataTransSnapshot.getValue(Transaksi::class.java)
//                                    val keyProduct = getdataTransSnapshot.key.toString()
//                                    val nama = transaksi?.nama
//                                    val nomor = transaksi?.nomor
//                                    val bukti = transaksi?.bukti
//                                    val hargaTotal = transaksi?.hargaTotal
//                                    val username = transaksi?.username
//                                    val status = transaksi?.status


                                    dataTrans.add(transaksi!!)
//                                    dataTrans.add(setData(keyProduct,username!!, nama!!, nomor!!,
//                                                            hargaTotal!!, bukti!!, status!!))
                                    Log.v("2311","Transaksi User == $transaksi")




                                }
                                if (dataTrans.isNotEmpty()){
                                    rv_transaction_progress.adapter = ProgressTransaksiAdapter(dataTrans){
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


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}