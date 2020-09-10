package com.bagicode.alfa3.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.product.AdminBuburBesarActivity
import kotlinx.android.synthetic.main.activity_bubur.*
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        tvTimBesar.setOnClickListener {
            startActivity(Intent(this@ProductActivity, AdminBuburBesarActivity::class.java))
        }

        tvTimKecil.setOnClickListener {

        }

        tvBuburBesar.setOnClickListener {

        }

        tvBuburKecil.setOnClickListener {

        }

        tvPudding.setOnClickListener {

        }

    }
}