package com.xiamen.www

import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout

/**
 * Created by admin on 2018/2/4.
 */

class Mytest1 : AppCompatActivity() {
    internal var lp = LinearLayout.LayoutParams(this, null)

    fun init() {
        lp.setMargins(0, 0, 0, 0)
    }
}
