package com.bagicode.alfa3.user.home.cart

import android.content.Context
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.utils.Preferences
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.*
import kotlin.math.log
import kotlin.math.min

open class CartAdapter(private var data: List<getCart>,
                       private val listener: (getCart) -> Unit)

    : RecyclerView.Adapter<CartAdapter.LeagueViewHolder>(){

    lateinit var ContextAdapter : Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_cart, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val parse = holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvHarga: TextView =  view.findViewById(R.id.tvHarga)
        private val tvJenis: TextView = view.findViewById(R.id.tvJenis)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        private val btn_min: ImageView = view.findViewById(R.id.btn_min)
        private val btn_plus: ImageView = view.findViewById(R.id.btn_plus)
        private val tvQty : TextView = view.findViewById(R.id.tvQty)
        lateinit var mDatabase : DatabaseReference
        private lateinit var preferences: Preferences
        val hashMap: HashMap<String, Int> = HashMap()
        var Qty = 1
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

        fun bindItem(data: getCart, listener: (getCart) -> Unit, context: Context, position: Int) {

            preferences = Preferences(context)
            mDatabase = FirebaseDatabase.getInstance().getReference("User")
                .child(preferences.getValues("user")
                    .toString())
                .child("cart")
                .child(data.key.toString())


            tvTitle.text = data.desc
            tvJenis.text = data.jenis
            tvQty.text = data.jumlah.toString()

            tvHarga.text = formatRupiah.format(data.harga!!.toDouble()* Qty)

            btn_min.setOnClickListener {
                Qty = (Qty - 1).coerceAtLeast(0)
                tvQty.text = Qty.toString()
                hashMap["jumlah"] = Qty
                tvHarga.text = formatRupiah.format(data.harga!!.toDouble() * Qty)

                mDatabase
                    .updateChildren(hashMap as Map<String, Int>)
            }
            btn_plus.setOnClickListener {
                Qty = (Qty + 1).coerceAtMost(10)
                tvQty.text = Qty.toString()
                hashMap["jumlah"] = Qty
                tvHarga.text = formatRupiah.format(data.harga!!.toDouble() * Qty)

                mDatabase
                    .updateChildren(hashMap as Map<String, Int>)


            }
            Glide.with(context)
                .load(data.url)
                .into(tvImage)
            Log.v("biasa", "Data Cartnya nih   "+data)

//            itemView.setOnClickListener {
//                listener(data)
//            }
        }

    }
}