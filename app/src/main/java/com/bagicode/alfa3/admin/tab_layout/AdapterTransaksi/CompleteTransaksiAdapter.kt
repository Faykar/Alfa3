package com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bumptech.glide.Glide

class CompleteTransaksiAdapter(private var dataTrans: List<Transaksi>,
                              private val listener: (Transaksi) -> Unit)
    : RecyclerView.Adapter<CompleteTransaksiAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_transaction_user, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(dataTrans[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = dataTrans.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvNama: TextView = view.findViewById(R.id.tvNama)
        private val tvNomor: TextView = view.findViewById(R.id.tvNomor)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        private val tvHargaTotal: TextView = view.findViewById(R.id.tvHargaTotal)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)




        fun bindItem(dataTrans: Transaksi, listener: (Transaksi) -> Unit, context : Context, position : Int) {


            if (dataTrans.status == "Complete") {
                tvNama.text = dataTrans.nama
                tvNomor.text = dataTrans.nomor
                tvStatus.text = dataTrans.status
                tvHargaTotal.text = dataTrans.hargaTotal.toString()

                Glide.with(context)
                    .load(dataTrans.bukti)
                    .into(tvImage)
            }


//            itemView.setOnClickListener {
//                listener(data)
//            }
        }

    }

}
