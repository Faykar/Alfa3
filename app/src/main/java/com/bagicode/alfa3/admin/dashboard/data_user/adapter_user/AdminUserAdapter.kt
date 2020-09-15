package com.bagicode.alfa3.admin.dashboard.data_user.adapter_user

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
import com.bagicode.alfa3.user.log.login.User
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class AdminUserAdapter(private var data: List<User>,
                             private val listener: (User) -> Unit)

    : RecyclerView.Adapter<AdminUserAdapter.LeagueViewHolder>(){

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_admin_user, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        private val et_nama: TextView = view.findViewById(R.id.et_nama)
        private val et_nomor: TextView = view.findViewById(R.id.et_nomor)
        private val et_password: TextView = view.findViewById(R.id.et_password)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)
        private val tvEdit: TextView = view.findViewById(R.id.tvEdit)

        fun bindItem(data: User, listener: (User) -> Unit, context: Context, position: Int) {


            tvUsername.text = data.username
            et_nama.text = data.nama
            et_nomor.text = data.nomor
            et_password.text = data.password

            Glide.with(context)
                .load(data.url)
                .into(tvImage)

            tvEdit.setOnClickListener {
                listener(data)
            }


//            itemView.setOnClickListener {
//
//                listener(data)
//            }
        }


//        private fun showUpdateDialog(
//            data: User,
//            ContextAdapter: Context
//        ) {
//            val builder = AlertDialog.Builder(ContextAdapter)
//
//            val inflater = LayoutInflater.from(ContextAdapter)
//            val view = inflater.inflate(R.layout.update_dialog, null)
//
//            val etTitle = view.findViewById<EditText>(R.id.et_title)
//            val etHarga = view.findViewById<EditText>(R.id.et_harga)
//            val etStok = view.findViewById<EditText>(R.id.et_stok)
//
//
//            etTitle.setText(data.desc)
//            etHarga.setText(data.harga.toString())
//            etStok.setText(data.stok.toString())
//
//
//
//            builder.setView(view)
//
//            builder.setPositiveButton("Update"){p0,p1 ->
//                val mDatabase = FirebaseDatabase.getInstance().reference.child("Bubur Besar")
//
//                val Title = etTitle.text.toString()
//                val Harga = etHarga.text.toString()
//                val Stok = etStok.text.toString()
//
//
//            }
//
//            builder.setNegativeButton("No"){p0,p1 ->
//
//            }
//
//        }
//
//
    }
}