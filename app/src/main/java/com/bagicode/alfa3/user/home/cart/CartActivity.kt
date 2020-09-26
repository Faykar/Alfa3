package com.bagicode.alfa3.user.home.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.user.home.payment.PaymentActivity
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var cartRef : DatabaseReference
    lateinit var transRef : DatabaseReference
    lateinit var listTransaksi: Transaksi

    lateinit var keyProduct : String
    lateinit var bukti: String

    private var total: Int = 0
    private var hargaTotal = total

    lateinit var preferences : Preferences
    private var data = ArrayList<getCart>()
    private var dataTransaction = ArrayList<Transaksi>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        data = intent.getSerializableExtra("data") as ArrayList<getCart>


        preferences = Preferences(applicationContext)
        cartRef = mFirebaseInstance.getReference("User")
            .child(preferences.getValues("user")
                .toString())
            .child("cart")



        transRef = mFirebaseInstance.getReference("User")
            .child(preferences.getValues("user").toString())




        rv_cart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()



    }


    private fun getData() {
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val takeCart = getdataSnapshot.getValue(getCart::class.java)
                    val keyProduct = getdataSnapshot.key.toString()
                    val harga = takeCart?.harga
                    val stok = takeCart?.stok
                    val jenis = takeCart?.jenis
                    val desc = takeCart?.desc
                    val url = takeCart?.url
                    data.add(setData(keyProduct, harga!!, stok!!, jenis!!, desc!!, url!!))
                }

                for (a in data.indices){
                    hargaTotal += data[a].harga!!
                }
                tvJumlah.text = hargaTotal.toString()
                val arrListCart = arrayListOf<Transaksi>()
//                val arrGetList = arrayListOf<Transaksi>()
//                var listTransaksi = intent.getParcelableExtra<Transaksi>("transaksi")

                btn_pay.setOnClickListener {
                    transRef.child("transaksi").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (getSnapshot in dataSnapshot.children) {
                                listTransaksi = getSnapshot.getValue(Transaksi::class.java)!!
                                val arrTrans = getSnapshot.getValue(Transaksi::class.java)!!

                                val keyProduct = getSnapshot.key.toString()
                                val nama = preferences.getValues("nama").toString()
                                val nomor = preferences.getValues("nomor").toString()
//                                val bukti = listTransaksi.bukti.toString()

                                arrListCart.add(listTransaksi)
                                dataTransaction.add(saveData(keyProduct,nama,nomor,hargaTotal))


                            }


                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                    val key =  transRef.child("transaksi")
                    .push()
                    .key
                    val nama = preferences.getValues("nama").toString()
                    val nomor = preferences.getValues("nomor").toString()

                    if (arrListCart.isEmpty()) {
                        transRef.child("transaksi")
                            .child(key.toString())
                            .setValue(saveData(key.toString(),nama, nomor, hargaTotal))

                    } else {
                        if (arrListCart.contains(saveData(key.toString(),nama,nomor,hargaTotal))) {
                        } else {
                            transRef.child("transaksi")
                                .push()
                                .setValue(saveData(key!!,nama,nomor, hargaTotal))
                        }
                    }

                    dataTransaction.add(saveData(key!!,nama, nomor, hargaTotal))

                    val intent = Intent(applicationContext,
                        PaymentActivity::class.java)
                        .putExtra("key",key)
                        .putExtra("harga",hargaTotal)
                        .putExtra("total", dataTransaction)
                    Log.v("dapat", "Valuenya "+dataTransaction)
                    startActivity(intent)


                }

                if (data.isNotEmpty()) {
                    rv_cart.adapter = CartAdapter(data) {



                    }
                }



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartActivity, "" + error.message, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun saveData(key: String, nama: String, nomor: String, hargaTotal: Int): Transaksi {
        val dataTrans = Transaksi (
            key,
            nama,nomor,
            hargaTotal
        )
        return dataTrans

    }

    private fun setData(key: String, harga: Int, stok: Int, jenis: String, desc: String, url: String): getCart {
        val data = getCart (
            key, harga, jenis, desc, url, stok
        )
        return data

    }


}