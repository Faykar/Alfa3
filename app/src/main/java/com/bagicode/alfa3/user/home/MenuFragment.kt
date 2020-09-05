package com.bagicode.alfa3.user.home


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bagicode.alfa3.R
import com.bagicode.alfa3.user.home.bubur.BuburActivity
import com.bagicode.alfa3.user.home.pudding.PuddingActivity
import com.bagicode.alfa3.user.riwayat.RiwayatActivity
import com.bagicode.alfa3.user.home.tim.TimActivity
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_bubur.setOnClickListener {
            startActivity(Intent(activity, BuburActivity::class.java))
        }

        iv_pudding.setOnClickListener {
            startActivity(Intent(activity, PuddingActivity::class.java))
        }
        iv_tim.setOnClickListener {
            startActivity(Intent(activity, TimActivity::class.java))
        }
        iv_riwayat.setOnClickListener {
            startActivity(Intent(activity, RiwayatActivity::class.java))
        }

    }





}
