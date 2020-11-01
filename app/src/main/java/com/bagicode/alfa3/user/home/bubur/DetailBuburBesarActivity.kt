package com.bagicode.alfa3.user.home.bubur

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_bubur.*
import com.bagicode.alfa3.user.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlin.properties.Delegates

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailBuburBesarActivity : AppCompatActivity() {

    lateinit var cart: DatabaseReference
    lateinit var preference: Preferences
    lateinit var refBubur : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bubur)

        val data = intent.getParcelableExtra<getBuburBesar>("data besar")
        val arrListCart = arrayListOf<getBuburBesar>()

        Log.v("testing","bubur besar "+data.desc.toString())
        Log.v("Kunci","Kunci "+data.key.toString())
        preference = Preferences(applicationContext)
        cart = FirebaseDatabase.getInstance()
                .getReference("User")
                .child(preference.getValues("user").toString())

        refBubur = FirebaseDatabase.getInstance()
            .getReference("Bubur Besar")
            .child(data.key.toString())

        cart.child("cart").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@DetailBuburBesarActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (getSnap in p0.children) {
                    arrListCart.add(getSnap.getValue(getBuburBesar::class.java)!!)
                }
            }

        })

        // Mengambil data dari Recycler View milik Bubur Besar
        val keyProduct = data.key.toString()
        val desc = data.desc
        val jenis = data.jenis
        val harga = data.harga
        val jumlah = data.jumlah
        val url = data.url
        val stok = data.stok!!.toInt()


        // Update Stok
        val updateStok = stok - 1



        tvStok.text = data.stok.toString()
        tvTitle.setText(desc)
        tvJenis.setText(jenis)
        tvHarga.setText(harga.toString())


        Glide.with(this)
                .load(data.url)
                .into(iv_poster_image)


        iv_back.setOnClickListener {
            finish()
        }

        btn_add.setOnClickListener {
            finish()
            if (stok >= 1) {
                if (arrListCart.isEmpty()) {
                    // Realtime Database Stok
                    val hashMap: HashMap<String, Any> = HashMap()
                    hashMap["stok"] = updateStok
                    refBubur
                        .updateChildren(hashMap as Map<String, Any>)
                    // Add to Cart User
                    cart.child("cart")
                        .push()
                        .setValue(addtoCart(keyProduct, harga!!, jenis, desc, jumlah!!,url))
                    Toast.makeText(
                        this@DetailBuburBesarActivity,
                        "Berhasil Menambah Ke Keranjang",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (arrListCart.contains(addtoCart(keyProduct, harga!!, jenis, desc, jumlah!!,url))) {
                        Toast.makeText(
                            this@DetailBuburBesarActivity,
                            "Produk Ini Sudah Ada Dikeranjang Anda",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else {
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap["stok"] = updateStok
                        refBubur
                            .updateChildren(hashMap as Map<String, Any>)
                        cart.child("cart")
                            .push()
                            .setValue(addtoCart(keyProduct, harga,jenis,desc,jumlah,url))
                        Toast.makeText(
                            this@DetailBuburBesarActivity,
                            "Berhasil Menambah Ke Keranjang",
                            Toast.LENGTH_LONG).show()
                    }
                }
            } else if (stok == 0) {
                Toast.makeText(
                    this@DetailBuburBesarActivity, "Stok Habis Silahkan Order Menu yang lain"
                    , Toast.LENGTH_SHORT
                )
                    .show()
            }

        }


    }

    private fun addtoCart(key: String, harga: Int, jenis: String?, desc: String?, jumlah:Int , url: String?) : getBuburBesar {
        val data = getBuburBesar (
            key, harga, jenis, desc,jumlah, url
        )
        return data

    }
}
