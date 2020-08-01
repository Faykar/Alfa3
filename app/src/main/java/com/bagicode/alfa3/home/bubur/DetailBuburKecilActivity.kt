package com.bagicode.alfa3.home.bubur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.home.bubur.model.getBuburKecil
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_bubur.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailBuburKecilActivity : AppCompatActivity() {


    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bubur)

        val data = intent.getParcelableExtra<getBuburKecil>("data kecil")

        mDatabase = FirebaseDatabase.getInstance().getReference("Bubur Kecil")
            .child(data.desc.toString())

        // Mengambil data dari Recycler View milik Bubur Kecil
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