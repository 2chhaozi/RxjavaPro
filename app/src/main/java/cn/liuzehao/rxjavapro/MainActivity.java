package cn.liuzehao.rxjavapro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/*
*
*   public final Disposable subscribe() {}
    // 表示观察者不对被观察者发送的事件作出任何响应（但被观察者还是可以继续发送事件）

    public final Disposable subscribe(Consumer<? super T> onNext) {}
    // 表示观察者只对被观察者发送的Next事件作出响应
    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}
    // 表示观察者只对被观察者发送的Next事件 & Error事件作出响应

    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 & Complete事件作出响应

    public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
    // 表示观察者只对被观察者发送的Next事件、Error事件 、Complete事件 & onSubscribe事件作出响应

    public final void subscribe(Observer<? super T> observer) {}
    // 表示观察者对被观察者发送的任何事件都作出响应
   */



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createOberverOne();
    }

    //订阅1
    private void createOberverOne(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "ObservableOnSubscribe//subscribe");
                //emitter事件发射器,产生事件并通知观察者
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "Observer//subscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "Observer//onNext//"+integer);
                if(integer == 2){
                    //该类可用来切断被观察者与观察者之间的连接
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observer//onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "Observer//onComplete");
            }
        });
    }

    //订阅2
    private void createObserverTwo(){
        Observable.just("just").subscribe(new Consumer<String>() {
            //每次接收到observable事件都会调用accept方法
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    //3
    private void createObserverThree(){
        //just(T...)：直接将传入的参数依次发送出来
        Observable observable = Observable.just("a", "b", "c");
        // 将会依次调用：
        // onNext("A");
        // onNext("B");
        // onNext("C");
        // onCompleted();
    }

    //4
    private void createObserverFour(){
        //fromArray(T[]) / fromArray(Iterable<? extends T>) : 将传入的数组 / Iterable 拆分成具体对象后，依次发送出来
        String[] words = new String[]{"a", "b", "c"};
        Observable observable = Observable.fromArray(words);
    }





}
