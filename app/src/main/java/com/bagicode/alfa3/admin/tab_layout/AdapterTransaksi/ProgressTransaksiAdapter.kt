package com.bagicode.alfa3.admin.tab_layout.AdapterTransaksi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.payment.Transaksi
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProgressTransaksiAdapter(private var dataTrans: List<Transaksi>,
                              private val listener: (Transaksi) -> Unit)
    : RecyclerView.Adapter<ProgressTransaksiAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_transaction_progress, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(dataTrans[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = dataTrans.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var mDatabase: DatabaseReference
        private val tvNama: TextView = view.findViewById(R.id.tvNama)
        private val tvNomor: TextView = view.findViewById(R.id.tvNomor)
        private val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        private val tvHargaTotal: TextView = view.findViewById(R.id.tvHargaTotal)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        private val tvEdit: TextView = view.findViewById(R.id.tvEdit)
        private val constraint: ConstraintLayout= view.findViewById(R.id.constraint)




        fun bindItem(dataTrans: Transaksi, listener: (Transaksi) -> Unit, context : Context, position : Int) {


            if (dataTrans.status!!.contentEquals("Progress")) {

                constraint.visibility = View.VISIBLE

                tvNama.text = dataTrans.nama
                tvNomor.text = dataTrans.nomor
                tvStatus.text = dataTrans.status
                tvHargaTotal.text = dataTrans.hargaTotal.toString()

                Glide.with(context)
                    .load(dataTrans.bukti)
                    .into(tvImage)



                tvEdit.setOnClickListener {
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["status"] = "Complete"

                    mDatabase = FirebaseDatabase.getInstance().getReference("User")

                    mDatabase
                        .child(dataTrans.username)
                        .child("transaksi")
                        .child(dataTrans.key.toString())
                        .updateChildren(hashMap as Map<String,String>)



                    listener(dataTrans)
                }
            } else {
               constraint.visibility = View.GONE
            }
        }


    }

}
