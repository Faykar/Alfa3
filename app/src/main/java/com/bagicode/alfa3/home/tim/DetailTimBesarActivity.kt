package com.bagicode.alfa3.home.tim

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.tim.model.getTimBesar
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_tim.*

class DetailTimBesarActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tim)

        val data = intent.getParcelableExtra<getTimBesar>("data besar")

        mDatabase = FirebaseDatabase.getInstance().getReference("Tim Besar")
            .child(data.desc.toString())


        // Mengambil data dari Recycler View milik Bubur Besar
        tvTitle.text = data.desc
        tvRP.text = ("Rp.")
        tvJenis.text = ("Besar")
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