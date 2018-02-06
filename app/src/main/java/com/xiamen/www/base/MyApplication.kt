package com.xiamen.www.base

import android.app.Application
import android.widget.Toast
import com.wanjian.cockroach.Cockroach

/**
 * Created by admin on 2018/2/6.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initCockroach()
    }

    private fun initCockroach() {
        Cockroach.install { thread, throwable ->
            Toast.makeText(baseContext, "Exception Happend\n" + thread + "\n" + throwable.toString(),
                    Toast.LENGTH_LONG).show()
        }
    }
}