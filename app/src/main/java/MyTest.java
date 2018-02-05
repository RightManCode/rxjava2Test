import android.net.Network;
import android.support.annotation.MainThread;
import android.util.Log;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.xiamen.www.bean.CategoryResultBean;
import com.xiamen.www.bean.FoodListBean;
import com.xiamen.www.bean.MobileAddressBean;
import com.xiamen.www.net.NetWork;
import com.xiamen.www.utils.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2018/2/4.
 */

public class MyTest {
    public void init() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                //formIterable的作用相当于遍历集合
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

    private void init1() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void init2() {
        Observable.just(1, 2, 3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void init3() {
        Observable.just(1, 2, 3, 4, 5)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
    }

    private void init4() {
        Observable<MobileAddressBean> observable1 = Rx2AndroidNetworking.get("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                .build()
                .getObjectObservable(MobileAddressBean.class);

        Observable<CategoryResultBean> observable2 = NetWork.INSTANCE.getGrankApi()
                .getCategoryData("Android", 1, 1);

        Observable.zip(observable1, observable2, new BiFunction<MobileAddressBean, CategoryResultBean, String>() {
            @Override
            public String apply(@NonNull MobileAddressBean mobileAddress, @NonNull CategoryResultBean categoryResult) throws Exception {
                return "合并后的数据为：手机归属地：" + mobileAddress.getResult().getMobilearea() + "人名：" + categoryResult.results.get(0).who;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e(TAG, "accept: 成功：" + s + "\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: 失败：" + throwable + "\n");
                    }
                });
    }
    private boolean isFromNet = false;
    public void init5() {
        Observable<FoodListBean> cache = Observable.create(new ObservableOnSubscribe<FoodListBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<FoodListBean> e) throws Exception {
                Log.e(TAG, "create当前线程:"+Thread.currentThread().getName() );
                FoodListBean data = CacheManager.getInstance().getFoodListData();

                // 在操作符 concat 中，只有调用 onComplete 之后才会执行下一个 Observable
                if (data != null){ // 如果缓存数据不为空，则直接读取缓存数据，而不读取网络数据
                    isFromNet = false;
                    Log.e(TAG, "\nsubscribe: 读取缓存数据:" );
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            mRxOperatorsText.append("\nsubscribe: 读取缓存数据:\n");
////                        }
//                    });

                    e.onNext(data);
                }else {
                    isFromNet = true;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mRxOperatorsText.append("\nsubscribe: 读取网络数据:\n");
//                        }
//                    });
                    Log.e(TAG, "\nsubscribe: 读取网络数据:" );
                    e.onComplete();
                }


            }
        });

        Observable<FoodListBean> network = Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .addQueryParameter("rows",10+"")
                .build()
                .getObjectObservable(FoodListBean.class);


        // 两个 Observable 的泛型应当保持一致

        Observable.concat(cache,network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodListBean foodListBean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
