package com.bagicode.alfa3.admin.dashboard.updateproduct

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.ProductActivity
import com.bumptech.glide.Glide
import com.bagicode.alfa3.user.home.bubur.model.getBuburBesar
import com.bagicode.alfa3.user.home.bubur.model.getBuburKecil
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UpdateBuburKecilActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var preference: Preferences

    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference

    lateinit var updateTitle : String
    lateinit var updateHarga : Number
    lateinit var updateStok: Number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val data = intent.getParcelableExtra<getBuburKecil>("data besar")
        preference = Preferences(applicationContext)


        // Mengambil data dari Recycler View milik Bubur Besar
        tvTitle.text = ("Bubur Besar")
        tvRP.text = ("Harga : ")
        tvJenis.text = ("Besar")
        var keyProduct = data.key.toString()

        val Title = data.desc.toString()
        val Harga = data.harga
        val Stok = data.stok

        et_title.setText(Title)
        et_harga.setText(Harga.toString())
        et_stok.setText(Stok.toString())


        Glide.with(this)
            .load(data.url)
            .into(iv_poster_image)


        iv_back.setOnClickListener {
            finish()
        }

        btn_update.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.show()
            mFirebaseInstance = FirebaseDatabase.getInstance()
            mFirebaseDatabase = mFirebaseInstance.getReference("Bubur Kecil")
                .child(keyProduct)

            updateTitle = et_title.text.toString()
            updateHarga = et_harga.text.toString().toInt()
            updateStok = et_stok.text.toString().toInt()

            // Data cannot Empty
            if (updateTitle.equals("")){
                et_title.error = "Masukkan Deskripsi"
                et_title.requestFocus()
            } else if (updateHarga.equals("")){
                et_harga.error = "Masukkan Harga"
                et_harga.requestFocus()
            } else if (updateStok.equals("")){
                et_stok.error = "Masukkan Stok"
                et_stok.requestFocus()
            } else {

                val hashMap: HashMap<String, Any> = HashMap()
                hashMap["desc"] = updateTitle
                hashMap["harga"] = updateHarga
                hashMap["stok"] = updateStok

                mFirebaseDatabase
                    .updateChildren(hashMap as Map<String, Any>)

                    .addOnSuccessListener {
                        progressDialog.onContentChanged()
                        progressDialog.setTitle("Data Berhasil ditambahkan")
                        progressDialog.show()

                        finishAffinity()
                        val intent = Intent (this@UpdateBuburKecilActivity,
                            ProductActivity::class.java)

                        startActivity(intent)

                    }

                    .addOnFailureListener{
                        progressDialog.setTitle("Data tidak boleh kosong")
                        progressDialog.show()


                    }



            }


        }

    }

}