package com.bagicode.alfa3.home.bubur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.google.firebase.database.FirebaseDatabase
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_bubur.*
import com.bagicode.alfa3.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.home.bubur.model.getBuburKecil
import com.google.firebase.database.DatabaseReference

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailBuburBesarActivity : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bubur)

        val data = intent.getParcelableExtra<getBuburBesar>("data besar")

        mDatabase = FirebaseDatabase.getInstance().getReference("Bubur Besar")
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

        btn_add.setOnClickListener {

        }

    }
}
