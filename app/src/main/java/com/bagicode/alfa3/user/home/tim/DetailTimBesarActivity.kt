package com.bagicode.alfa3.user.home.tim

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.tim.model.getTimBesar
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_tim.*
import kotlinx.android.synthetic.main.activity_detail_tim.iv_back
import kotlinx.android.synthetic.main.activity_detail_tim.iv_poster_image
import kotlinx.android.synthetic.main.activity_detail_tim.tvHarga
import kotlinx.android.synthetic.main.activity_detail_tim.tvJenis
import kotlinx.android.synthetic.main.activity_detail_tim.tvStok
import kotlinx.android.synthetic.main.activity_detail_tim.tvTitle

class DetailTimBesarActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var cart: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tim)


        val data = intent.getParcelableExtra<getTimBesar>("data besar")
        val arrListCart = arrayListOf<getTimBesar>()
        mDatabase = FirebaseDatabase.getInstance().getReference("Tim Besar")
            .child(data.desc.toString())

        preference = Preferences(applicationContext)
        cart = FirebaseDatabase.getInstance()
            .getReference("User")
            .child(preference.getValues("user").toString())

        cart.child("cart").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@DetailTimBesarActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (getSnap in p0.children) {
                    arrListCart.add(getSnap.getValue(getTimBesar::class.java)!!)
                }
            }

        })


        // Mengambil data dari Recycler View milik Bubur Kecil
        val keyProduct = data.key.toString()
        val desc = data.desc
        val jenis = data.jenis
        val harga = data.harga
        val url = data.url

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
            if (arrListCart.isEmpty()) {
                cart.child("cart")
                    .push()
                    .setValue(addtoCart(keyProduct,harga!!,jenis,desc,url))
                Toast.makeText(
                    this@DetailTimBesarActivity,
                    "Berhasil Menambah Ke Keranjang",
                    Toast.LENGTH_LONG).show()
            } else {
                if (arrListCart.contains(addtoCart(keyProduct,harga!!,jenis,desc,url))) {
                    Toast.makeText(
                        this@DetailTimBesarActivity,
                        "Produk Ini Sudah Ada Dikeranjang Anda",
                        Toast.LENGTH_LONG).show()
                } else {
                    cart.child("cart").push().setValue(addtoCart(keyProduct,harga!!,jenis,desc,url))
                    Toast.makeText(
                        this@DetailTimBesarActivity,
                        "Berhasil Menambah Ke Keranjang",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun addtoCart(key: String, harga: Int, jenis: String?, desc: String?, url: String?): getTimBesar {
        val data = getTimBesar (
            key, harga, jenis, desc, url
        )
        return data

    }
}