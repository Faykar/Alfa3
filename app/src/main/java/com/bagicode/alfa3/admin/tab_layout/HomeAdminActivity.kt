package com.bagicode.alfa3.admin.tab_layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_home_admin.*
import com.bagicode.alfa3.R
import com.bagicode.alfa3.admin.dashboard.DashboardActivity
import com.bagicode.alfa3.admin.dashboard.ProductActivity
import com.bagicode.alfa3.admin.dashboard.data_user.UserActivity
import com.bagicode.alfa3.admin.sign.LoginAdminActivity
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


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.dashboard) {
            startActivity(Intent(this@HomeAdminActivity, DashboardActivity::class.java))
        } else if (id == R.id.data_user){
            startActivity(Intent(this@HomeAdminActivity, UserActivity::class.java))
        } else if (id == R.id.data_product){
            startActivity(Intent(this@HomeAdminActivity, ProductActivity::class.java))
        } else if (id == R.id.data_transaction){

        } else if (id == R.id.tvLogout){
            finishAffinity()

            Toast.makeText(this@HomeAdminActivity, "Berhasil Logout", Toast.LENGTH_SHORT)
                .show()


            val intent = Intent(this@HomeAdminActivity,
                LoginAdminActivity::class.java)
            startActivity(intent)

        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}