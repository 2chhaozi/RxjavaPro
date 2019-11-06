package cn.liuzehao.rxjavapro.RxjavaBaseUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by liuzehao on 2019/8/23.
 */
public class ThreadUseActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";
    private Observable<String> observable;
    private Consumer<String> observer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        observable = Observable.just("线程调度");
        observer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "接收数据//"+s+"//"+Thread.currentThread());
            }
        };
    }


    /**
     *subscribeOn() & observerOn()
     *作用:线程控制，即指定被观察者 & 观察者的工作线程类型
     *在rxjava中内置了多种用于调度的线程类型
     *
     *     类型                          含义                   应用场景
     *
     * Schedulers.immediate()          当前线程=不指定线程         默认
     *
     * AndroidSchedulers.mainThread()   Android主线程             操作UI
     *
     * Schedulers.newThread()           常规新线程                耗时等操作
     *
     * Schedulers.io()                  io操作线程              网络请求、读写文件等io密集型操作
     *
     * Schedulers.computation           CPU计算操作线程            大量计算操作
     */

    private void useNormal(){
        //在主线程中创建被观察者，因此被观察者在主线程操作
        Observable<String> observable = Observable.just("在主线程中创建被观察者");
        //在主线程中创建观察者，因此观察者在主线程操作
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }

    private void useNote1(){
        /**
         *若observable.subscribeOn()多次指定被观察者生产事件的线程，则只有第一次指定有效其余指定无效
         *若observer.observerOn()多次指定观察者接收&响应事件的线程，则每次指定均有效，即每指定一次，jiuhui
         * 进行一次线程切换
         */
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     *在请求网络时退出当前的Activity，在数据返回时会程序崩溃
     *此时需要在退出Activity的时候使用Disposable.dispose()来切断观察者与被观察者的联系
     *当出现多个Disposable时，可采用RxJava内置容器CompositeDisposable进行统一管理
     *
     * 添加Disposable到CompositeDisposable容器
     * CompositeDisposable.add()
     *
     * 清空CompositeDisposable容器
     * CompositeDisposable.clear()
     */

}
