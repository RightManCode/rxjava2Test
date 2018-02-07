package com.xiamen.www.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.xiamen.www.R
import com.xiamen.www.base.DetailsHtmlPageActivity
import com.xiamen.www.ui.adapter.BaseViewPagerAdapter
import com.xiamen.www.ui.fragment.OperatorsFragment
import com.xiamen.www.ui.fragment.UseCasesFragment
import com.xiamen.www.utils.ScreenUtil
import com.xiamen.www.utils.StatusBarUtl
import kotlinx.android.synthetic.main.activity_view.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val titles = listOf("操作符", "例子")
    private lateinit var mPagerAdapter: BaseViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            toolbar_title.layoutParams.height = ScreenUtil.dip2px(this, 80F)
        }
        StatusBarUtl.fullScreen(this)
        //初始化titleBar
        initViewPager()
        //初始化控件点击事件
        initViewClick()
    }

    private fun initViewClick() {
        RxView.clicks(fab)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    val intent = Intent(this, DetailsHtmlPageActivity::class.java)
                    intent.putExtra("url", "https://github.com/RightManCode")
                    startActivity(intent)
                }
    }

    private fun initViewPager() {
        mPagerAdapter = BaseViewPagerAdapter(supportFragmentManager, titles)
        mPagerAdapter.addFragment(OperatorsFragment())
        mPagerAdapter.addFragment(UseCasesFragment())
        home_viewPager.adapter = mPagerAdapter
        home_tabLayout.setupWithViewPager(home_viewPager)
    }
}
