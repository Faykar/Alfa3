package com.bagicode.alfa3.user.riwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.pudding.model.addPudding
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_riwayat.*

class RiwayatActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Pudding")
    val arrayUser = arrayListOf<addPudding>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        btn_save.setOnClickListener {
            val desc = desc.text.toString()
            val url = url.text.toString()
            val jenis = jenis.text.toString()
            val stok = stok.text.toString().toInt()
            val harga = hrg.text.toString().toInt()
            addingPudding(desc,url,jenis,stok,harga)
            Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_SHORT).show()

        }
    }
    private fun addingPudding(desc: String, url: String, jenis: String, stok: Int, harga: Int){
        val pudding = addPudding(
            desc,
            url,
            jenis,
            stok,
            harga
        )
        myRef.push().setValue(pudding)
    }



}
