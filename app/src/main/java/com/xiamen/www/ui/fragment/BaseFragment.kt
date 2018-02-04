package com.xiamen.www.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by admin on 2018/2/1.
 */

abstract class BaseFragment : Fragment() {
    /**
     * 获取布局ID
     */
    protected abstract val contentViewLayoutID: Int

    /**
     * 界面初始化
     */
    protected abstract fun init()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (contentViewLayoutID != 0) {
            inflater.inflate(contentViewLayoutID, container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

}
