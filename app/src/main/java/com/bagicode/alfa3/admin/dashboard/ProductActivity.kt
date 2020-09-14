package com.bagicode.alfa3.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.product.*
import kotlinx.android.synthetic.main.activity_bubur.*
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        tvTimBesar.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminTimBesarActivity::class.java))

        }

        tvTimKecil.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminTimKecilActivity::class.java))

        }

        tvBuburBesar.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminBuburBesarActivity::class.java))

        }

        tvBuburKecil.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminBuburKecilActivity::class.java))

        }

        tvPudding.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminPuddingActivity::class.java))

        }

    }
}