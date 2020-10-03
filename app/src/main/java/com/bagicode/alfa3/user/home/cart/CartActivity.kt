package com.bagicode.alfa3.user.home.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    lateinit var listCart: getCart
    lateinit var listData: isiTransaksi

    lateinit var keyProduct: String
    lateinit var bukti: String

    private var total: Int = 0
    private var hargaTotal = total

    lateinit var preferences: Preferences
    private var dataCart = ArrayList<getCart>()
    private var listIsiData = ArrayList<isiTransaksi>()
    private var dataTransaction = ArrayList<Transaksi>()

//    Klo ada array yang buat dipanggil ditempat lain, jgn ditaro didalem fungsi, taro dipaling atas biar bisa kepanggil difungsi yang lain
    val arrListCart = arrayListOf<Transaksi>()

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

//        Button adanya disini, jadi fungsi getData cuma buat ngambil data dari database, trus ngisi arrayListCart
        btn_pay.setOnClickListener {
            val arrListTransaksi = arrayListOf<isiTransaksi>()

            val isiKey = Ref.child("cart")
                    .push()
                    .key
            listData = isiTransaksi()

            val isiHarga = listData.harga
            val isiJenis = listData.jenis
            val isiDesc = listData.desc

            val key = transRef.child("transaksi")
                    .push()
                    .key
            val nama = preferences.getValues("nama").toString()
            val username = preferences.getValues("user").toString()
            val nomor = preferences.getValues("nomor").toString()
            val status = ("on going")

            transRef.child("transaksi").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (getSnapshot in dataSnapshot.children) {
                        listTransaksi = getSnapshot.getValue(Transaksi::class.java)!!
                        val arrTrans = getSnapshot.getValue(Transaksi::class.java)!!

                        val keyProduct = getSnapshot.key.toString()
                        val nama = preferences.getValues("nama").toString()
                        val username = preferences.getValues("user").toString()
                        val nomor = preferences.getValues("nomor").toString()
//                                val bukti = listTransaksi.bukti.toString()

                        arrListCart.add(listTransaksi)
                        dataTransaction.add(saveData(keyProduct, username, nama, nomor, hargaTotal, status))
                        Log.v("transaksi", "transaksi" + listTransaksi)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
                            .show()
                }

            })

            Ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listIsiData.clear()
                    for (getdataSnapshot in dataSnapshot.children) {
                        listData = getdataSnapshot.getValue(isiTransaksi::class.java)!!
                        val isi = getdataSnapshot.getValue(isiTransaksi::class.java)
                        val keyProduct = getdataSnapshot.key.toString()
                        val jenis = isi?.jenis
                        val harga = isi?.harga
                        val desc = isi?.desc
                        val url = isi?.url


                        listIsiData.add(isiData(keyProduct, harga!!, jenis!!, desc!!,url!!))
                        arrListTransaksi.add(listData)
                        Log.v("isinya", "List Isi Transaksi" + listData)
                        isiRef.child("transaksi")
                                .child(key.toString())
                                .child("Pesanan")
                                .push()
                                .setValue(isiData(keyProduct, harga, jenis, desc, url!!))

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
                            .show()
                }

            })

            if (arrListCart.isEmpty()) {
                transRef.child("transaksi")
                        .child(key.toString())
                        .setValue((saveData(key.toString(),username, nama, nomor, hargaTotal, status)))


            } else {
                if (arrListCart.contains(saveData(key.toString(), username, nama, nomor, hargaTotal, status))) {
                } else {
                    transRef.child("transaksi")
                            .child(key.toString())
                            .setValue(saveData(key!!, username, nama, nomor, hargaTotal, status))
                }
            }

//                    if (arrListTransaksi.isEmpty()){
//                        isiRef.child("transaksi")
//                            .child(key.toString())
//                            .push()
//                            .setValue(isiData(isiKey.toString(), isiHarga!! ,isiJenis!!, isiDesc!!))
//                    } else {
//                        if (arrListTransaksi.contains(isiData(isiKey.toString(),isiHarga!!,isiJenis!!,isiDesc!!))){
//                        } else {
//                            isiRef.child("transaksi")
//                                .child(key.toString())
//                                .push()
//                                .setValue(isiData(isiKey.toString(), isiHarga!! ,isiJenis!!, isiDesc!!))
//                        }
//
//                    }

            dataTransaction.add(saveData(key!!, username, nama, nomor, hargaTotal, status))
            finish()
            val intent = Intent(applicationContext,
                    PaymentActivity::class.java)
                    .putExtra("key", key)
                    .putExtra("harga", hargaTotal)
                    .putExtra("total", dataTransaction)
            Log.v("dapat", "Valuenya " + key)
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
                    val keyProduct = getdataSnapshot.key.toString()
                    val harga = takeCart?.harga
                    val stok = takeCart?.stok
                    val jenis = takeCart?.jenis
                    val desc = takeCart?.desc
                    val url = takeCart?.url
                    dataCart.add(setData(keyProduct, harga!!, stok!!, jenis!!, desc!!, url!!))
                    Log.v("blablabla", "Data cartnya apa nih   " + takeCart)
                }

                if (dataCart.isNotEmpty()) {
                    rv_cart.adapter = CartAdapter(dataCart) {
                    }
                }
                for (a in dataCart.indices) {
                    hargaTotal += dataCart[a].harga!!
                }
                tvJumlah.text = hargaTotal.toString()

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
        val dataTrans = Transaksi(
                key,
                username,
                nama, nomor,
                hargaTotal,
                status
        )
        return dataTrans

    }

    private fun setData(key: String, harga: Int, stok: Int, jenis: String, desc: String, url: String): getCart {
        val data = getCart(
                key, harga, jenis, desc, url, stok
        )
        return data

    }


}