@file:Suppress("NAME_SHADOWING")

package com.bagicode.alfa3.user.home.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.payment.PaymentActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.user.home.payment.isiTransaksi
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity() : AppCompatActivity() {
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var cartRef: DatabaseReference
    lateinit var transRef: DatabaseReference
    lateinit var Ref: DatabaseReference
    lateinit var isiRef: DatabaseReference
    lateinit var listTransaksi: Transaksi

    lateinit var keyProduct: String
    lateinit var bukti: String

    private var total: Int = 0
    private var hargaTotal = total

    lateinit var preferences: Preferences
    private var dataCart = ArrayList<getCart>()
    private var dataTransaction = ArrayList<Transaksi>()

//    Klo ada array yang buat dipanggil ditempat lain, jgn ditaro didalem fungsi, taro dipaling atas biar bisa kepanggil difungsi yang lain
    val arrListTransaksi = arrayListOf<Transaksi>()
    val arrListisiTransaksi = arrayListOf<isiTransaksi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        dataCart = intent.getSerializableExtra("data") as ArrayList<getCart>
        preferences = Preferences(applicationContext)
        cartRef = mFirebaseInstance.getReference("User")
                .child(preferences.getValues("user")
                        .toString())
                .child("cart")
        Ref = mFirebaseInstance.getReference("User")
                .child(preferences.getValues("user")
                        .toString())
                .child("cart")
        isiRef = mFirebaseInstance.getReference("User")
                .child(preferences.getValues("user")
                        .toString())
        transRef = mFirebaseInstance.getReference("User")
                .child(preferences.getValues("user")
                        .toString())

        Log.v("usernama", "Username is "+preferences.getValues("user"))

        rv_cart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()

        iv_back.setOnClickListener {
            finish()

        }


//        Button adanya disini, jadi fungsi getData cuma buat ngambil data dari database, trus ngisi arrayListCart
        if (hargaTotal >= 0) {
            btn_pay.visibility = View.VISIBLE
        } else {
            btn_pay.visibility = View.INVISIBLE
        }
            btn_pay.setOnClickListener {

                val Transkey = transRef.child("transaksi")
                    .push()
                    .key
                val nama = preferences.getValues("nama")
                    .toString()
                val username = preferences.getValues("user")
                    .toString()
                val nomor = preferences.getValues("nomor")
                    .toString()
                val status = ("on going")

                transRef.child("transaksi")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (getSnapshot in dataSnapshot.children) {
                                listTransaksi = getSnapshot.getValue(Transaksi::class.java)!!

                                val keyProduct = getSnapshot.key.toString()
                                val nama = preferences.getValues("nama")
                                    .toString()
                                val username = preferences.getValues("user")
                                    .toString()
                                val nomor = preferences.getValues("nomor")
                                    .toString()

                                arrListTransaksi.add(listTransaksi)
                                dataTransaction.add(
                                    saveData(
                                        keyProduct,
                                        username,
                                        nama,
                                        nomor,
                                        hargaTotal,
                                        status
                                    )
                                )
                                Log.v("transaksi", "transaksi" + listTransaksi)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                this@CartActivity,
                                "" + error.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                    })

//            Ref.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    dataCart.clear()
//                    for (getdataSnapshot in dataSnapshot.children) {
//                        listCart = getdataSnapshot.getValue(getCart::class.java)!!
//                        val isi = getdataSnapshot.getValue(getCart::class.java)
//                        val keyProduct = getdataSnapshot.key.toString()
//                        val jenis = isi?.jenis
//                        val harga = isi?.harga
//                        val desc = isi?.desc
//                        val url = isi?.url
//
//
////                        listIsiData.add(isiData(keyProduct, harga!!, jenis!!, desc!!,url!!))
////                        arrListisiTransaksi.add(listCart)
//                        Log.v("isinya", "List Isi Transaksi" + listCart)
//
//
//                        val isiKey = isiRef.child("transaksi")
//                            .child(key.toString())
//                            .child("Pesanan")
//                            .push()
//                            .key
//
//                        isiRef.child("transaksi")
//                                .child(key.toString())
//                                .child("Pesanan")
//                                .push()
//                                .setValue(setData(isiKey.toString(), harga!!, jenis!!, desc!!, url!!))
//
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
//                            .show()
//                }
//
//            })

                if (arrListTransaksi.isEmpty() && arrListisiTransaksi.isEmpty()) {
                    transRef.child("transaksi")
                        .child(Transkey.toString())
                        .setValue(
                            (saveData(
                                Transkey.toString(),
                                username,
                                nama,
                                nomor,
                                hargaTotal,
                                status
                            ))
                        )


                } else {
                    if (arrListTransaksi.contains(
                            saveData(
                                Transkey.toString(),
                                username,
                                nama,
                                nomor,
                                hargaTotal,
                                status
                            )
                        )
                    ) {
                    } else {
                        transRef.child("transaksi")
                            .child(Transkey.toString())
                            .setValue(
                                saveData(
                                    Transkey!!,
                                    username,
                                    nama,
                                    nomor,
                                    hargaTotal,
                                    status
                                )
                            )
                    }
                }

                for (a in dataCart.indices) {

                    val harga = dataCart[a].harga!!.toInt()
                    val jenis = dataCart[a].jenis.toString()
                    val desc = dataCart[a].desc.toString()
                    val key = dataCart[a].key.toString()
                    val url = dataCart[a].url.toString()


                    if (arrListisiTransaksi.isEmpty()) {
                        isiRef.child("transaksi")
                            .child((Transkey.toString()))
                            .child("Pesanan")
                            .push()
                            .setValue(isiData(key, harga, jenis, desc, url))
                    } else {
                        if (arrListisiTransaksi.contains(isiData(key, harga, jenis, desc, url))) {
                        } else {
                            isiRef.child("transaksi")
                                .child(Transkey.toString())
                                .child("Pesanan")
                                .push()
                                .setValue(isiData(key, harga, jenis, desc, url))
                        }

                    }
                }

                dataTransaction.add(saveData(Transkey!!, username, nama, nomor, hargaTotal, status))
                finish()
                val intent = Intent(
                    applicationContext,
                    PaymentActivity::class.java
                )
                    .putExtra("key", Transkey)
                    .putExtra("harga", hargaTotal)
                    .putExtra("total", dataTransaction)
                Log.v("dapat", "Valuenya " + Transkey)
                Log.v("dapat1", "Valuenya " + hargaTotal)
                Log.v("dapat2", "Valuenya " + dataTransaction)
                startActivity(intent)
            }


    }


    private fun getData() {
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataCart.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val takeCart = getdataSnapshot.getValue(getCart::class.java)
                    val takeIsi = getdataSnapshot.getValue(isiTransaksi::class.java)
                    val keyProduct = getdataSnapshot.key.toString()
                    val jumlah = takeCart?.jumlah
                    val harga = takeCart?.harga!!
                    val jenis = takeCart.jenis
                    val desc = takeCart.desc
                    val url = takeCart.url
                    dataCart.add(setData(keyProduct, harga, jenis!!, desc!!,jumlah!!, url!!))
                    arrListisiTransaksi.add(takeIsi!!)
                    Log.v("blablabla", "Data cartnya apa nih   " + takeCart)
                    Log.v("bleble", "Data isinya apa nih"+takeIsi)
                }

                if (dataCart.isNotEmpty()) {
                    rv_cart.adapter = CartAdapter(dataCart) {

                    }
                for (a in dataCart.indices) {
                    hargaTotal += dataCart[a].harga!! * dataCart[a].jumlah!!
                    tvJumlah.text = hargaTotal.toString()
                    Log.v("total", "Total price is $hargaTotal")
                }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
                        .show()
            }

        })
    }


    private fun isiData(
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

    private fun saveData(key: String, username: String, nama: String, nomor: String, hargaTotal: Int, status: String): Transaksi {
        return Transaksi(
                key,
                username,
                nama, nomor,
                hargaTotal,
                status
        )
    }

    private fun setData(key: String, harga: Int, jenis: String, desc: String, jumlah: Int, url: String): getCart {
        return getCart(
                key, harga, jenis, desc, jumlah, url
        )
    }
}