package com.bagicode.alfa3.admin.tab_layout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
        PendingFragment(),
        ProgressFragment(),
        CompleteFragment()
    )



    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> PendingFragment()
            1 -> ProgressFragment()
            2 -> CompleteFragment()

        }
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pending"
            1 -> "Progress"
            else -> "Complete"
        }
    }
}