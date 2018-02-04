package com.xiamen.www.ui.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xiamen.www.R
import com.xiamen.www.bean.OperatorModel
import com.xiamen.www.ui.adapter.OperatorsAdapter
import kotlinx.android.synthetic.main.fragment_operators.*

/**
 * Created by admin on 2018/2/1.
 */

abstract class CategoryBaseFragment : BaseFragment() {

     var data: MutableList<OperatorModel>? = null

    override val contentViewLayoutID: Int
        get() = R.layout.fragment_operators

    protected abstract fun fillData()

    protected abstract fun itemClick(position: Int)

    override fun init() {
        fillData()
        val adapter = object : OperatorsAdapter(data) {
            override fun onItemClick(position: Int) {
                itemClick(position)
            }
        }
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        operators_recycler.layoutManager = LinearLayoutManager(activity)
        operators_recycler.itemAnimator = DefaultItemAnimator()
        operators_recycler.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        operators_recycler.adapter = adapter

    }
}
