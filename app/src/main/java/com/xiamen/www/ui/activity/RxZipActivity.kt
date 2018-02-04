package com.xiamen.www.ui.activity

import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_rx_operator_base.*

/**
 * Created by admin on 2018/2/4.
 * zip
 * <p>
 * 合并事件专用
 * <p>
 * 分别从两个上游事件中各取出一个组合
 * 一个事件只能被使用一次，顺序严格按照事件发送的顺序
 * 最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)
 * <p>
 */
class RxZipActivity : RxOperatorBaseActivity() {
    private val s: String = "RxZipActivity"
    override fun isShowBack(): Boolean {
        return false
    }

    override fun getSubTitle(): CharSequence? {
        return "Zip"
    }

    override fun doSomething() {
        Observable.zip(getStringObservable(), getIntObservable(), BiFunction<String, Int, String> { t1, t2 ->
            t1 + t2
        }).subscribe {
            rx_operators_text.append("zip:" + it+"\n")
        }
    }


    private fun getStringObservable(): Observable<String> {
        return Observable.create({
            if (!it.isDisposed) {
                it.onNext("A")
                rx_operators_text.append("String emit:A" + "\n")
                Log.e(s, "String emit:A" + "\n")

                it.onNext("B")
                rx_operators_text.append("String emit:B" + "\n")
                Log.e(s, "String emit:B" + "\n")

                it.onNext("C")
                rx_operators_text.append("String emit:C" + "\n")
                Log.e(s, "String emit:C" + "\n")
            }
        })
    }

    private fun getIntObservable(): Observable<Int> {
        return Observable.create<Int> {
            if (!it.isDisposed) {
                it.onNext(1)
                rx_operators_text.append("Int emit:1" + "\n")
                Log.e(s, "Int emit:1" + "\n")

                it.onNext(2)
                rx_operators_text.append("Int emit:2" + "\n")
                Log.e(s, "Int emit:2" + "\n")

                it.onNext(3)
                rx_operators_text.append("Int emit:3" + "\n")
                Log.e(s, "Int emit:3" + "\n")

                it.onNext(4)
                rx_operators_text.append("Int emit:4" + "\n")
                Log.e(s, "Int emit:4" + "\n")

                it.onNext(5)
                rx_operators_text.append("Int emit:5" + "\n")
                Log.e(s, "Int emit:5" + "\n")
            }
        }
    }

}