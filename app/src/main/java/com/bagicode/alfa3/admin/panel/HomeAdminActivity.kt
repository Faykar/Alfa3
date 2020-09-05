package com.bagicode.alfa3.admin.panel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_home_admin.*
import com.bagicode.alfa3.R
import com.google.android.material.navigation.NavigationView

class HomeAdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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

        viewpager_main.adapter = PagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)

        nav_view.setNavigationItemSelectedListener(this)


        
    }

    private fun setSupportActionBar(toolbar: Toolbar) {

    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return mToggle.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.dashboard -> {
                Toast.makeText(this,"Data Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.data_user -> {
                Toast.makeText(this,"Data Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.data_product -> {
                Toast.makeText(this,"Data Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.data_transaction -> {
                Toast.makeText(this,"Data Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.tvLogout -> {
                Toast.makeText(this,"Data Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
        return true
    }
}