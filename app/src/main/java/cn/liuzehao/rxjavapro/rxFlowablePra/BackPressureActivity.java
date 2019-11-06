package cn.liuzehao.rxjavapro.rxFlowablePra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import cn.liuzehao.rxjavapro.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzehao on 2019/9/3.
 */
public class BackPressureActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";
    private Button subBtn;// 该按钮用于调用Subscription.request（long n ）
    private Subscription subscription;// 用于保存Subscription对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backpressurelayout);
        subBtn = findViewById(R.id.sub_btn);
        //newSubcribe2();
        asySubscribe();
    }

    private void newSubcribe(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

            }
        }, BackpressureStrategy.ERROR)// 需要传入背压参数BackpressureStrategy
                .subscribe(new Subscriber<Integer>() {
                    // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                    // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                    // 不同点：Subscription增加了void request(long n)
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void newSubscribe1(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "发送事件 1");
                e.onNext(1);
                Log.d(TAG, "发送事件 2");
                e.onNext(2);
                Log.d(TAG, "发送事件 3");
                e.onNext(3);
                Log.d(TAG, "发送事件 4");
                e.onNext(4);
                Log.d(TAG, "发送完成");
                e.onComplete();


            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(3);
                        /**
                         *作用：决定观察者能够接收多少个事件
                         *如上设置，说明观察者能够接收3个事件（多出的事件存放在缓存区）
                         *官方默认推荐使用Long.MAX_VALUE
                         */
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
        /**
         *对于异步订阅情况（2者工作在不同线程），若观察者没有设置s.request()，即说明观察者不接收事件
         *此时被观察者仍能继续发送事件（存放在缓存区），等观察者需要时再取出被观察者事件
         *当缓存区存满时（128个事件），就会溢出报错
         *缓存区大小 = 128 有Flowable的buffer大小决定
         */
    }

    private void newSubcribe2() {
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscription.request(2);
            }
        });

        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.d(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        // 保存Subscription对象，等待点击按钮时（调用request(2)）观察者再接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    //缓存溢出
    private void oomSubscribe(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送129个事件，即超出了缓存区的大小
                for(int i = 0; i < 129; i++){
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        // 默认不设置可接收事件大小
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
        /**
         *结果：
         *     发送了xxx事件
         *     onError
         *     XXX Exception
         */
    }

    //同步订阅
    private void synSubcribe1(){
        /**
         *不会出现被观察者发送事件速度 > 观察者接收事件速度的情况,
         *可是，却会出现被观察者发送事件数量 > 观察者接收事件数量的问题
         *出现了不匹配情况
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 被观察者发送事件数量 = 4个
                Log.d(TAG, "发送了事件1");
                emitter.onNext(1);
                Log.d(TAG, "发送了事件2");
                emitter.onNext(2);
                Log.d(TAG, "发送了事件3");
                emitter.onNext(3);
                Log.d(TAG, "发送了事件4");
                emitter.onNext(4);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件 " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
        /**
         *结果：
         *    onSubscribe
         *    发送了事件1
         *    接收到了事件 1
         *    发送了事件2
         *    接收到了事件 2
         *    发送了事件3
         *    接收到了事件 3
         *    发送了事件4
         *    onError
         *    xxx Exception
         */

        /**
         *结论：
         *     若观察者没有设置Subscription.request(long n),那么被观察者就默认观察者没有处理事件能力，
         *     此时被观察者开始发送第1个事件后不会再发送，
         *     那么被观察者将不会收到被观察者的任何事情 & 抛出MissingBackpressureException异常
         *
         *     异常原因：
         *              被观察者每发送1次事件就会等待观察者接收完再继续发送事件
         *              既然观察者无法接收事件，那被观察者一直等待会耗费资源，所以会抛出异常来提醒观察者接收事件
         *
         */
    }

    private void synSubcribe2(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();
                Log.d(TAG, "观察者可接收事件" + n);
                for(int i = 0; i < n; i++){
                    // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        // 设置观察者每次能接受10个事件
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
        /**
         *FlowableEmitter.requested()特性：
         *      1.可叠加性：观察者可连续要求接收事件，被观察者会进行叠加并一起发送
         *                 Subscription.request（a1）；
         *                 Subscription.request（a2）；
         *                 FlowableEmitter.requested()的返回值 = a1 + a2
         *
         *      2.实时更新性：每次发送事件后，FlowableEmitter.requested()的其返回值会实时更新观察者能接受的事件
         *                   1.即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
         *                   2.仅计算Next事件，complete & error事件不算
         *                   Subscription.request（10）；
         *                   // FlowableEmitter.requested()的返回值 = 10
         *
         *                   FlowableEmitter.onNext(1); // 发送了1个事件
         *                   // FlowableEmitter.requested()的返回值 = 9
         *      3.异常：
         *              当FlowableEmitter.requested()的返回值 = 0时，则代表观察者不可接收事件
         *              当被观察者若继续发送事件，则会抛出MissingBackpressureException异常
         *
         *              观察者无调用Subscription.request(),FlowableEmitter.requested()的返回值 = 0
         *              因为同步订阅关系中，不存在缓存区概念
         */
    }

    /**
     *在异步订阅关系中，被观察者不能根据观察者自身接收事件能力控制发送事件的速度，因为处在不同线程中
     *只能通过反向控制原理：通过Rxjava内部固定调用被观察者线程中的request(n)从而反向控制被观察者的发送事件速度
     */
    private void asySubscribe(){
        /**
         *被观察者：一共需要发送500个事件，但真正开始发送事件的前提 = FlowableEmitter.request()返回值 ≠ 0
         *观察者：每次接收事件数量 = 48（点击按钮）
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                boolean flag;//设置标记控制位

                for(int i = 0; i < 500; i++){
                    flag = false;
                    //若requested() == 0则不发送，这里会无限循环将外层循环卡住
                    //这里会自动判断实时的需发送的数量，初始是128
                    while (emitter.requested() == 0){
                        if(!flag){
                            flag = true;
                        }
                    }
                    // requested() ≠ 0 才发送
                    Log.e(TAG, "发送了事件" + i + "，观察者可接收事件数量 = " + emitter.requested());
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.e(TAG, "onSubscribe");
                        subscription = s;
                        // 初始状态 = 不接收事件；通过点击按钮接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

        subBtn.setOnClickListener(null);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮则接收48个事件
                subscription.request(48);
            }
        });
    }


    /**
     *BackpressureStrategy:
     *         EORROR:直接抛出异常MissBackpressureException
     *         MISSING:友好提示：缓存区满了（还是会抛异常）
     *         BUFFER:缓存区大小设置成无限大（注意防止oom）
     *         DROP:超过缓存区大小（128）的事件丢弃（观察者再次request也无法获取被丢弃的事件）
     *         LATEST:只保存最新（最后）事件，超过缓存区大小（128）的事件直接丢弃
     *         （即如果发生150个事件，缓存区会保存129个事件【第1个 到 第128 + 第150个事件】）
     */
    private void backPressureM(){
        /**
         *注意：
         *     Flowable可通过自己创建，或通过其它方式自动创建，如interval操作符；
         *     interval会每隔1段时间，从0开始每次递增1，直至无穷大；默认运行在新线程上
         *     与timer操作符区别：timer操作符可结束发送
         *     此时的被观察者无法使用背压模式参数，需要用封装好的背压模式方案：
         *     onBackpressureBuffer()
         *     onBackpressureDrop()
         *     onBackpressureLatest()
         */
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        subscription = s;
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: " + aLong);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

}
