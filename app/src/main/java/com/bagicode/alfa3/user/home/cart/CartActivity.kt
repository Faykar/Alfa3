package com.bagicode.alfa3.user.home.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.payment.PaymentActivity
import com.bagicode.alfa3.user.log.login.User
import com.bagicode.alfa3.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mFirebaseDatabase: DatabaseReference
    lateinit var cartRef : DatabaseReference
    lateinit var cart: getCart

    private var total: Int = 0

    lateinit var preferences : Preferences
    private var data = ArrayList<getCart>()
    private var dataUser = ArrayList<User>()



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










        rv_cart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getData()

    }


    private fun getData() {
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    val takeCart = getdataSnapshot.getValue(getCart::class.java)
                    val key = getdataSnapshot.key.toString()
                    val harga = takeCart?.harga
                    val stok = takeCart?.stok
                    val jenis = takeCart?.jenis
                    val desc = takeCart?.desc
                    val url = takeCart?.url
                    data.add(setData(key, harga!!, stok!!, jenis!!, desc!!, url!!))
                }
                for (a in data.indices){
                    total += data[a].harga!!
                }
                tvJumlah.setText(total.toString())
                btn_pay.setOnClickListener {

                    val intent = Intent(applicationContext,
                    PaymentActivity::class.java).putExtra("data", data)
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

    private fun setData(key: String, harga: Int, stok: Int, jenis: String, desc: String, url: String): getCart {
        val data = getCart (
            key, harga, jenis, desc, url, stok
        )
        return data

    }


}