package com.xiamen.www.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import com.xiamen.www.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_view_pager.*

/**
 * Created by admin on 2018/2/7.
 */
class ViewPagerActivity1 : AppCompatActivity() {
    private lateinit var pagerAdapter: PagerAdapter
    private var imgList: MutableList<View>? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        imgList = MutableList(3, {
            val imageView = ImageView(this)
            imageView.setImageDrawable(resources.getDrawable(R.drawable.ad))
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView
        })

        pagerAdapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return imgList!!.size
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
//                super.destroyItem(container, position, `object`)
                container?.removeView(imgList!![position])
            }

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                container?.addView(imgList!![position])
                return imgList!![position]
            }
        }

        view_pager.adapter = pagerAdapter
    }

}