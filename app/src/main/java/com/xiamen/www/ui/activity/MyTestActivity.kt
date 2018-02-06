package com.xiamen.www.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.xiamen.www.R
import com.xiamen.www.utils.BaseDisposableObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

/**
 * Created by admin on 2018/1/31.
 */

class MyTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //创建事件源
        initObservable()
        //初始化控件点击事件
        initViewClick()
    }

    private fun initViewClick() {
        var viewClick = View.OnClickListener {
            when (it.id) {
                R.id.bt_login -> {
                    //RxBind防止多次点击（间隔时间：2s）
                    RxView.clicks(bt_login)
                            .throttleFirst(3000, TimeUnit.MILLISECONDS)
                            .subscribe({
                                Toast.makeText(this, "防止连续点击多次触发View的点击事件Demo,3秒内按钮只能点击1次", Toast.LENGTH_SHORT).show()
                                //测试Predicate
                                Observable.interval(2000, TimeUnit.MILLISECONDS)
                                        .filter {
                                            Log.e(toString(), "it:" + it)
                                            if (it > 2) {
                                                throw Exception("asdf")
                                            }
                                            true
                                        }.subscribe(object : BaseDisposableObserver() {
                                    override fun onNext(t: Long) {
                                        Log.e(this.toString(), "onNext:")
                                    }
                                })
                            })
                }
                else -> {
                    Toast.makeText(this, "hello！", Toast.LENGTH_SHORT).show()
                }
            }
        }
//        //防止控件被多次点击
//        RxView.clicks(bt_login)
//                .throttleFirst(2000, TimeUnit.MILLISECONDS) //2秒内只取一个点击事件，防止抖动
        bt_login.setOnClickListener(viewClick)
        et_user_name.setOnClickListener(viewClick)
        et_pass_word.setOnClickListener(viewClick)

    }

    private fun initObservable() {
        val observableEmail = RxTextView.textChanges(et_user_name)
        val observablePassword = RxTextView.textChanges(et_pass_word)
        Observable.combineLatest(observableEmail, observablePassword,
                BiFunction<CharSequence, CharSequence, Boolean>
                { s, s2 -> isEmailValid(s.toString()) && isPasswordValid(s2.toString()) })
                .subscribe(object : Observer<Boolean> {
                    private var i: Int = 0
                    private var mDisposable: Disposable? = null

                    override fun onError(e: Throwable) {

                    }

                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                        // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    }

                    override fun onComplete() {

                    }

                    override fun onNext(t: Boolean) {
                        i++
//                        if (i>10){
//                            mDisposable!!.dispose()
//                        }
                        bt_login.isEnabled = t
                    }
                })
//        DisposableObserver<Boolean>() {
//            override fun onNext(t: Boolean) {
//                bt_login.isEnabled = t
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//        }
    }

    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
}
