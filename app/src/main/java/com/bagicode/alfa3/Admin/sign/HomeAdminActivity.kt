package com.bagicode.alfa3.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_home_admin.*
import com.bagicode.alfa3.R
class HomeAdminActivity : AppCompatActivity() {
    private lateinit var mToggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        // memunculkan tombol burger menu
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // untuk toggle open dan close navigation
        mToggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        // tambahkan mToggle ke drawer_layout sebagai pengendali open dan close drawer
        drawer_layout.addDrawerListener(mToggle)
        mToggle.syncState()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return mToggle.onOptionsItemSelected(item)
    }
}