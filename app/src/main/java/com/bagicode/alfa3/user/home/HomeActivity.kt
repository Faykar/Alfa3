package com.bagicode.alfa3.user.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.dashboard.DashboardFragment
import com.bagicode.alfa3.user.log.login.User
import kotlinx.android.synthetic.main.activity_home.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeActivity : AppCompatActivity() {

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentMenu = MenuFragment()
        val fragmentProfile = ProfileFragment()

        val fragmentHome = DashboardFragment()
        setFragment(fragmentHome)

        iv_menu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(iv_menu1, R.drawable.ic_home_active)
            changeIcon(iv_menu2, R.drawable.ic_menu)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }
        iv_menu2.setOnClickListener {
            setFragment(fragmentMenu)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_menu_active)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }
        iv_menu3.setOnClickListener {

            setFragment(fragmentProfile)

            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_menu)
            changeIcon(iv_menu3, R.drawable.ic_profile_active)
        }
    }

    protected fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTranscation = fragmentManager.beginTransaction()
        fragmentTranscation.replace(R.id.layout_frame, fragment)
        fragmentTranscation.commit()
    }

    private fun changeIcon (imageView: ImageView, int: Int){
        imageView.setImageResource(int)
}
}
