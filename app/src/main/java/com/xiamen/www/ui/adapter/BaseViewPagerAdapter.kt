package com.xiamen.www.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by admin on 2018/2/2.
 */

class BaseViewPagerAdapter(fm: FragmentManager, private val title: List<String>) : FragmentPagerAdapter(fm) {
    private val mFragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }
}
