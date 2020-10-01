package com.bagicode.alfa3.user.home.cart

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.*

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

        fun bindItem(data: getCart, listener: (getCart) -> Unit, context: Context, position: Int) {


            tvTitle.text = data.desc
            tvJenis.text = data.jenis

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))


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