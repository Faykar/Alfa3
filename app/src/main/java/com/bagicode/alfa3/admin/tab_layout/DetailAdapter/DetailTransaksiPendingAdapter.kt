package com.bagicode.alfa3.admin.tab_layout.DetailAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.data_transaction.Pesanan.Pesanan
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailTransaksiPendingAdapter(private var data: ArrayList<Pesanan>,
                                    private val listener: (Pesanan) -> Unit)
    : RecyclerView.Adapter<DetailTransaksiPendingAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_detail_pesanan, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvHarga: TextView =  view.findViewById(R.id.tvHarga)
        private val tvJenis: TextView = view.findViewById(R.id.tvJenis)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)



        fun bindItem(data: Pesanan, listener: (Pesanan) -> Unit, context: Context, position: Int) {


            tvTitle.text = data.desc
            tvJenis.text = data.jenis


            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))


            Glide.with(context)
                .load(data.url)
                .into(tvImage)

        }

    }

}
