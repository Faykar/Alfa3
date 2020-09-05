package com.bagicode.alfa3.user.home.tim

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.tim.model.getTimKecil
import com.bumptech.glide.Glide

class TimKecilAdapter(private var data: List<getTimKecil>,
                      private val listener: (getTimKecil) -> Unit)

    : RecyclerView.Adapter<TimKecilAdapter.LeagueViewHolder>(){

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_menu, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvJenis: TextView = view.findViewById(R.id.tvJenis)
        private val tvRP: TextView = view.findViewById(R.id.tvRP)
        private val tvStok: TextView = view.findViewById(R.id.tvStok)
        private val tvHarga: TextView =  view.findViewById(R.id.tvHarga)

        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: getTimKecil, listener: (getTimKecil) -> Unit, context: Context, position: Int) {

            tvTitle.text = data.desc
            tvRP.text = ("Rp.")
            tvJenis.text = ("Kecil")
            tvStok.text = data.stok.toString()
            tvHarga.text = data.harga.toString()

            Glide.with(context)
                .load(data.url)
                .into(tvImage);

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }
}