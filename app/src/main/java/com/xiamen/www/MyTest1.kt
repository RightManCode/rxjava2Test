package com.xiamen.www

import java.util.ArrayList

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * Created by admin on 2018/2/7.
 */

class MyTest1 {
    private var imgList: List<View>? = null
    private var pagerAdapter: PagerAdapter? = null

    private fun init() {
        imgList = ArrayList()
        pagerAdapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 0
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view==`object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                super.destroyItem(container, position, `object`)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {

                return super.instantiateItem(container, position)
            }
        }
    }
}
