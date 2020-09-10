package com.bagicode.alfa3.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bagicode.alfa3.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        data_product.setOnClickListener{
            startActivity(Intent(this@DashboardActivity, ProductActivity::class.java))
        }

        data_user.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, UserActivity::class.java))
        }

        data_transaction.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, TransactionActivity::class.java))
        }

    }
}