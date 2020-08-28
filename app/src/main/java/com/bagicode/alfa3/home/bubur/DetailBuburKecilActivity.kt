package com.bagicode.alfa3.home.bubur

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.bubur.model.getBuburKecil
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_bubur.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailBuburKecilActivity : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference
    lateinit var cart: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bubur)

        val data = intent.getParcelableExtra<getBuburKecil>("data kecil")
        val arrListCart = arrayListOf<String>()

        mDatabase = FirebaseDatabase.getInstance().getReference("Bubur Kecil")
            .child(data.desc.toString())

        preference = Preferences(applicationContext)
        cart = FirebaseDatabase.getInstance()
                .getReference("User")
                .child(preference.getValues("user").toString())

        cart.child("cart").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (getSnap in p0.children) {
                    arrListCart.add(getSnap.getValue(String::class.java).toString())
                }
            }
        })

        // Mengambil data dari Recycler View milik Bubur Besar
        var keyProduct = data.key.toString()
        tvTitle.text = data.desc
        tvRP.text = ("Rp.")
        tvJenis.text = ("Kecil")
        tvStok.text = data.stok.toString()
        tvHarga.text = data.harga.toString()
        Glide.with(this)
                .load(data.url)
                .into(iv_poster_image)


        iv_back.setOnClickListener {
            finish()
        }

        button.setOnClickListener {
            if (arrListCart.isEmpty()) {
                addToCart(keyProduct)
//                TOAS DIGANTI JADI DIALOG
                Toast.makeText(
                        this@DetailBuburKecilActivity,
                        "Berhasil Menambah Ke Keranjang",
                        Toast.LENGTH_LONG).show()
            } else {
                if (arrListCart.contains(keyProduct)) {
//                TOAS DIGANTI JADI DIALOG
                    Toast.makeText(
                            this@DetailBuburKecilActivity,
                            "Produk Ini Sudah Ada Dikeranjang Anda",
                            Toast.LENGTH_LONG).show()
                } else {
                    addToCart(keyProduct)
//                TOAS DIGANTI JADI DIALOG
                    Toast.makeText(
                            this@DetailBuburKecilActivity,
                            "Berhasil Menambah Ke Keranjang",
                            Toast.LENGTH_LONG).show()
                }
            }

        }

    }

    private fun addToCart(key: String){
        cart.child("cart").push().setValue(key)
    }
}