package com.bagicode.alfa3.admin.dashboard.data_product.adapter_product

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.tim.model.getTimKecil
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class AdminTimKecilAdapter(private var data: List<getTimKecil>,
                           private val listener: (getTimKecil) -> Unit)

    : RecyclerView.Adapter<AdminTimKecilAdapter.LeagueViewHolder>(){

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_admin_menu, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvRP: TextView = view.findViewById(R.id.tvRP)
        private val tvStok: TextView = view.findViewById(R.id.tvStok)
        private val tvHarga: TextView =  view.findViewById(R.id.tvHarga)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        private val tvEdit: TextView = view.findViewById(R.id.tvEdit)
        private val tvDelete: TextView = view.findViewById(R.id.tvDelete)

        fun bindItem(data: getTimKecil, listener: (getTimKecil) -> Unit, context: Context, position: Int) {


            tvTitle.text = data.desc
            tvRP.text = ("Rp.")
            tvStok.text = data.stok.toString()
            tvHarga.text = data.harga.toString()

            Glide.with(context)
                .load(data.url)
                .into(tvImage)

            tvEdit.setOnClickListener {
                listener(data)
                showUpdateDialog(data, context)
            }


//            itemView.setOnClickListener {
//
//                listener(data)
//            }
        }


        private fun showUpdateDialog(
            data: getTimKecil,
            ContextAdapter: Context
        ) {
            val builder = AlertDialog.Builder(ContextAdapter)

            val inflater = LayoutInflater.from(ContextAdapter)
            val view = inflater.inflate(R.layout.update_dialog, null)

            val etTitle = view.findViewById<EditText>(R.id.et_title)
            val etHarga = view.findViewById<EditText>(R.id.et_harga)
            val etStok = view.findViewById<EditText>(R.id.et_stok)


            etTitle.setText(data.desc)
            etHarga.setText(data.harga.toString())
            etStok.setText(data.stok.toString())



            builder.setView(view)

            builder.setPositiveButton("Update"){p0,p1 ->
                val mDatabase = FirebaseDatabase.getInstance().reference.child("Bubur Besar")

                val Title = etTitle.text.toString()
                val Harga = etHarga.text.toString()
                val Stok = etStok.text.toString()


            }

            builder.setNegativeButton("No"){p0,p1 ->

            }

        }


    }
}
