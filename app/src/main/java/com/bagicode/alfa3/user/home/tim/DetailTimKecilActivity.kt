package com.bagicode.alfa3.user.home.tim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.tim.model.getTimKecil
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_tim.*

class DetailTimKecilActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tim)

        val data = intent.getParcelableExtra<getTimKecil>("data kecil")

        mDatabase = FirebaseDatabase.getInstance().getReference("Tim Kecil")
            .child(data.desc.toString())


        // Mengambil data dari Recycler View milik Bubur Besar
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
    }
}