package cn.liuzehao.rxjavapro.RxjavaBaseUse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import rx.functions.Action1;

/**
 * Created by liuzehao on 2019/7/30.
 * Rxjava基本操作符
 */
public class BaseUseActivity extends AppCompatActivity {
    private static final String TAG = "liuzehao";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //创建操作符
    private void createM(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 传入参数： OnSubscribe 对象
            // 当 Observable 被订阅时，OnSubscribe 的 call() 方法会自动被调用，即事件序列就会依照设定依次被触发
            // 即观察者会依次调用对应事件的复写方法从而响应事件
            // 从而实现由被观察者向观察者的事件传递 & 被观察者调用了观察者的回调方法 ，即观察者模式
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象 产生 & 发送事件
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                // 注：建议发送事件前检查观察者的isUnsubscribed状态，以便在没有观察者时，让Observable停止发射数据
                /*if (!observer.isUnsubscribed()) {

                }*/
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //快速创建操作符1
    private void fastCreateM1(){
        //在创建后就会发送这些对象，相当于执行了onNext("a")、onNext("b")、onNext("c")
        Observable.just("a", "b", "c").subscribe(new Observer<String>() {

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
        });
    }

    //快速创建操作符2
    private void fastCreateM2(){
        //逻辑同上
        final String[] strs = {"a", "b", "c"};
        Observable.fromArray(strs).subscribe(new Observer<String>() {
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
        });
    }

    //快速创建操作符3
    private void fastCreateM3(){
        //逻辑同上，适合发送多个数据
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //其他相关创建操作符
    private void fastCreateM4(){
        // 下列方法一般用于测试使用

        /*<-- empty()  -->
         该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
         即观察者接收后会直接调用onCompleted（）*/
        Observable observable1=Observable.empty();

        /*<-- error()  -->
         该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
         可自定义异常
         即观察者接收后会直接调用onError（）*/
        Observable observable2=Observable.error(new RuntimeException());

        /*<-- never()  -->
         该方法创建的被观察者对象发送事件的特点：不发送任何事件
         即观察者接收后什么都不调用*/
        Observable observable3=Observable.never();
    }

    private Integer i = 10;
    //延迟创建操作符1
    private void delayCreateM1(){
        //直到有观察者订阅时才创建被观察者对象及发送事件
        //保证当前被观察者发送的数据是最新的

        //此时观察者还没被创建
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i += 1;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                //此时会取i 的第二次赋值
                Log.e(TAG, "onNext//"+integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //延迟创建操作符2
    private void delayCreateM2(){
        //快速创建一个被观察者对象并指定延迟时间发送事件，并且发送的事件是0
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext//"+ aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //延迟创建操作符3
    private void delayCreateM3(){
        //快速创建一个被观察者对象，并每隔一段时间发送事件
        //同时发送的时间是从0开始无限加1递增的事件

        //参数1：第一次延迟的时间；参数2：间隔发送的时间；
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext//"+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //延迟创建操作符4
    private void delayCreateM4(){
        //发送有一定数量的事件

        //参数1：事件序列的起始点；参数2：发送的事件数量；参数3：第一次延迟多少秒发送；参数4：事件发送时间间隔
        Observable.intervalRange(3, 10,2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext//"+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    //延迟创建操作符5
    private void delayCreateM5(){
        //无延迟发送一定数量事件(rangeLong也一样，只是支持的类型不一样)
        Observable.range(3, 10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext//"+integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }


    //------------------------------------------------------

    //变换操作符1
    private void convertM1(){
        //对被观察者发送的每一个事件通过指定函数处理，从而变换成另一个事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "Map变换操作符-->整型:"+integer+"-->String类型:"+integer;
            }
        }).subscribe(new Consumer<String>() {//这里的观察者只接收被观察者的onNext()事件
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept//"+s);
            }
        });
    }

    //变换操作符2
    private void convertM2(){
        /*
        * 作用：将被观察者发送的事件序列进行拆分和单独转换，再合并成一个新的事件序列，最后再进行发送
        * 原理：
        * 1.为事件序列中每个事件都创建一个 Observable 对象；
        * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
        * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
        * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
        *
        * 注意：新合并生成的事件序列顺序是无序的，即与旧序列发送事件的顺序无关
       */
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
                List<String> convertStr = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    convertStr.add("原事件是："+integer+"--->拆分后的子事件："+i);
                }
                return Observable.fromIterable(convertStr);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept//"+s);
            }
        });
    }

    //变换操作符3
    private void convertM3(){
        //ConcatMap()变换操作符和flatMap()类似，只不过合并新生成的事件序列和旧的事件序列顺序一样
    }

    //变换操作符4
    private void converM4(){
        //作用：定期从被观察者需要发送的事件中获取一定数量的事件 & 放到缓存区中，最终发送
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)//设置缓存区大小 & 步长（缓存区大小 = 每次从被观察者中获取的事件数量；步长 = 每次获取新事件的数量）
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        for(Integer integer : integers){
                            Log.e(TAG, "onNext//事件："+integer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //------------------------------------------------------------

    //组合、合并操作符
    private void concatM1(){
        //组合多个被观察者一起发送（数量：<=4）
        //注：按发送顺序串行执行
        Observable.concat(Observable.just(1, 2, 3, 4),
                Observable.just(5, 6, 7, 8))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        //组合被观察者数量（>4）
        //注：按发送顺序串行执行
        Observable.concatArray(Observable.just(1, 2, 3, 4),
                Observable.just(1, 2, 3, 4),
                Observable.just(5, 6, 7, 8),
                Observable.just(1, 2, 3, 4),
                Observable.just(5, 6, 7, 8))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void concatM2(){
        //组合多个被观察者一起发送，合并后 按时间线并行执行（<=4）
        //即--->0，2，  1，3， 2，4
        Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS)
        , Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // mergeArray（） = 组合4个以上的被观察者一起发送数据，此处不作过多演示，类似concatArray（）
    }

    private void concatM3(){
        //使用该操作符能够使得即使有一个被观察者发送了onError()事件，也能够等待所有被观察者将事件发送完再发送onError()事件
        //不会让被观察者发送事件终止
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new NullPointerException());
                emitter.onComplete();
            }
        }), Observable.just(3, 4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext//"+integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });

        //mergeDelayError()操作符同理
    }

    private void contactM4(){
        /*作用：合并多个被观察者发送的事件，产生一个新的事件序列（即组合后的事件序列）并最终发送
        * 注意：事件组合方式 = 严格按照原先事件序列进行对位合并
        * 最终合并的事件数量 = 多个被观察者中数量最少的数量
        * */
        Observable observable1 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.e(TAG, "被观察者1发送了事件1");
                emitter.onNext(1);
                Thread.sleep(1000);
                Log.e(TAG, "被观察者1发送了事件2");
                emitter.onNext(2);
                Thread.sleep(1000);
                Log.e(TAG, "被观察者1发送了事件3");
                emitter.onNext(3);
                Thread.sleep(1000);
            }
        });

        Observable observable2 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.e(TAG, "被观察者2发送了事件A");
                emitter.onNext(1);
                Thread.sleep(1000);
                Log.e(TAG, "被观察者2发送了事件B");
                emitter.onNext(2);
                Thread.sleep(1000);
                Log.e(TAG, "被观察者2发送了事件C");
                emitter.onNext(3);
                Thread.sleep(1000);
                Log.e(TAG, "被观察者2发送了事件D");
                emitter.onNext(3);
                Thread.sleep(1000);
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {

            @Override
            public String apply(Integer a, String b) throws Exception {
                return a + b;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG, "最终接收到的事件 = " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });

        /*
        * 特别注意：
        * 尽管被观察者2的事件D没有事件与其合并，但还是会继续发送
        * 若在被观察者1 & 被观察者2的事件序列最后发送onComplete()事件，则被观察者2的事件D也不会发送
        * */
    }

    private void contactM5(){
        /*
        * 作用：当2个observable中的任何一个发送数据之后，将先发送数据的observable的最后（最新）的一个数据
        *      与另外一个observable发送的每个数据结合，最终基于该函数的结果发送数据（同一个时间点上合并）
        * */
        Observable.combineLatest(Observable.just(1L, 2L, 3L),
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                new BiFunction<Long, Long, Long>(){

                    @Override
                    public Long apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        Log.e(TAG, "合并的数据是： "+ o1 + " "+ o2);
                        return o1 + o2;
                        // 合并的逻辑 = 相加
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long s) throws Exception {
                        Log.e(TAG, "合并的结果是： "+s);
                    }
        });

        /*
        * 输出结果：
        *         合并数据是：3 0
        *         3
        *         合并数据是：3 1
        *         4
        *         合并数据是：3 2
        *         5
        *
        * */

        /*
        * combineLatestDelayError（）
        * 作用类似于concatDelayError（） / mergeDelayError（） ，即错误处理，此处不作过多描述
        * */

    }

    private void contactM6(){
        /*
        * 作用：把被观察者需要发送的事件聚合成一个事件&发送
        * 注意：聚合的逻辑根据需求撰写，本质上是前2个数据聚合，将聚合后的事件与下一个事件继续聚合，以此类推
        * */
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer a, Integer b) throws Exception {
                        Log.e(TAG, "本次计算的数据是 "+a + "乘" + b);
                        return a * b;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "最终计算结果是 "+integer);
                    }
                });

        /*
         * 输出结果：
         *         本次计算的数据是 1乘2
         *         本次计算的数据是 2乘3
         *         本次计算的数据是 6乘4
         *         最终计算结果是 24
         * */
    }

    private void contactM7(){
        //作用：将被观察者发送的事件收集到一个数据结构后发送
        Observable.just(1, 2, 3, 4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() {//创建收集数据的数据结构
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {//用创建的数据结构收集数据
                    @Override
                    public void accept(ArrayList<Integer> list, Integer value) throws Exception {
                        list.add(value);
                    }
                })
                .subscribe(new Consumer<ArrayList<Integer>>() {
                    @Override
                    public void accept(ArrayList<Integer> s) throws Exception {
                        Log.e(TAG, "本次发送的数据是："+s);
                    }
                });

    }

    private void contactM8(){
        //作用：在一个被观察者发送事件前，追加发送一些数据/一个新的被观察者
        Observable.just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "startWith//1//收到的数据是："+integer);
                    }
                });

        Observable.just(4, 5, 6)
                .startWith(Observable.just(1, 2, 3))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "startWith//2//收到的数据是："+integer);
                    }
                });

    }

    private void contactM9(){
        //作用：统计被观察者发送的事件数量
        Observable.just(1, 2, 3, 4, 5)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "发送的事件数量//"+aLong);
                    }
                });
    }

    private void scenesM1(){
        /**
         *作用：使得被观察者延迟一段时间再发送事件
         *
         * 1. 指定延迟时间
         * 参数1 = 时间；参数2 = 时间单位
         * delay(long delay,TimeUnit unit)
         *
         * 2. 指定延迟时间 & 调度器
         * 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器
         * delay(long delay,TimeUnit unit,mScheduler scheduler)
         *
         * 3. 指定延迟时间  & 错误延迟
         * 错误延迟，即：若存在Error事件，则如常执行，执行后再抛出错误异常
         * 参数1 = 时间；参数2 = 时间单位；参数3 = 错误延迟参数
         * delay(long delay,TimeUnit unit,boolean delayError)
         *
         * 4. 指定延迟时间 & 调度器 & 错误延迟
         * 参数1 = 时间；参数2 = 时间单位；参数3 = 线程调度器；参数4 = 错误延迟参数
         * delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError): 指定延迟多长时间并添加调度器，错误通知可以设置是否延迟
         */

        Observable.just(1, 2, 3, 4)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "delay//"+integer);
                    }
                });

    }

    private void scenesM2(){
        //do()：在某个事件的生命周期中调用
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("发生错误了"));
            }
        })
        //1.当Observable每发送1次数据事件就会调用1次
        .doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Log.e(TAG, "doOnEach: " + integerNotification.getValue());
            }
        })
        //2.执行Next事件前调用
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "doOnNext: " + integer);
            }
        })
        //3.执行Next事件后调用
        .doAfterNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "doAfterNext: " + integer);
            }
        })
        //4.Observable正常发送事件完毕后调用
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doOnComplete");
            }
        })
        //5.Observable发送错误事件时调用
        .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "doOnError: " + throwable.getMessage());
            }
        })
        //6.观察者订阅时调用
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.e(TAG, "doOnSubscribe");
            }
        })
        //7.Observable发送事件完毕后调用，无论正常发送完毕/异常终止
        .doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doAfterTerminate");
            }
        })
        //8.最后执行
        .doFinally(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doFinally");
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收到事件："+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "接收到Error事件");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "接收到Complete事件");
            }
        });
    }

    private void scenesM3(){
        //错误处理：在发送事件过程中，遇到错误时的处理机制

        //遇到错误时，发送1个特殊事件&正常终止，可捕获在它之前发生的异常
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable("发生错误了"));
            }
        })
        .onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                Log.e(TAG, "在onErrorReturn处理了错误："+throwable.toString());
                return 666;
            }
        })
        .subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收到了事件"+ integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });

        /**
         *返回结果：
         *
         * 接收到了事件1
         * 接收到了事件2
         * 在onErrorReturn处理了错误：java.lang.Throwable:发生了错误
         * 接收到了事件666
         * 对Complete事件作出响应
         */
    }

    private void scenesM4(){
        /**
         *作用：遇到错误时，发送一个新的Observable
         *注意：
         *      1.onErrorResumeNext()拦截的错误 = Throwable；若需拦截Exception请用
         *      onExceptionResumeNext
         *
         *      2.若onErrorResumeNext()拦截的错误 = Exception，则会将错误传递给观察者的onError方法
         *
         *      3.onExceptionResumeNext（）拦截的错误 = Exception；若需拦截Throwable请用onErrorResumeNext（）
         *
         *      4.若onExceptionResumeNext（）拦截的错误 = Throwable，则会将错误传递给观察者的onError方法
         *
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable("发送错误了"));
            }
        })
        .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                //1.捕捉错误异常
                Log.e(TAG, "在onErrorReturn处理了错误: "+throwable.toString() );
                //2.发生错误事件后，发送一个新的被观察者 & 发送事件序列
                return Observable.just(11, 12);
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收到了事件："+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发生错误了"));
            }
        })
        .onExceptionResumeNext(new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                observer.onNext(11);
                observer.onNext(22);
                observer.onComplete();
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "接收到了事件："+integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void scenesM5(){
        /**
         *作用：重试，即当出现错误时，让被观察者重新发射数据
         *注意：
         *    1.接收到onError()时，重新订阅&发送事件
         *    2.Throwable和Exception都可拦截
         *总共5种重载方法：
         *
         * <-- 1. retry（） -->
         * 作用：出现错误时，让被观察者重新发送数据
         * 注：若一直错误，则一直重新发送
         *
         * <-- 2. retry（long time） -->
         * 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制）
         * 参数 = 重试次数
         *
         * <-- 3. retry（Predicate predicate） -->
         * 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
         * 参数 = 判断逻辑
         *
         * <--  4. retry（new BiPredicate<Integer, Throwable>） -->
         * 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
         * 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
         *
         * <-- 5. retry（long time,Predicate predicate） -->
         * 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制）
         * 参数 = 设置重试次数 & 判断逻辑
         */

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发送错误了"));
                emitter.onNext(3);
            }
        })
        .retry()
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发送错误了"));
                emitter.onNext(3);
            }
        })
        //拦截错误后，判断是否需要重新发送请求
        .retry(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                return true;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发送错误了"));
                emitter.onNext(3);
            }
        })
        .retry(new BiPredicate<Integer, Throwable>() {
            @Override
            public boolean test(Integer integer, Throwable throwable) throws Exception {
                // 捕获异常
                Log.e(TAG, "异常错误 =  "+throwable.toString());
                // 获取当前重试次数
                Log.e(TAG, "当前重试次数 =  "+integer);
                return true;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发送错误了"));
                emitter.onNext(3);
            }
        })
        .retry(3, new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return true;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void scenesM6(){
        /**
         *retryUntil()
         *作用：出现错误后，判断是否需要重新发送数据
         *     1.若需要重新发送 & 持续遇到错误，则持续重试
         *     2.作用类似retry(Predicate predicate),区别是：返回true则不重新发送事件
         */
    }

    private void scenesM7(){
        /**
         *作用：遇到错误时，将发生的错误传递给一个新的被观察者，并决定是否需要重新订阅原始被观察者 & 发生事件
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("发生错误了"));
                emitter.onNext(3);
            }
        })
        .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        // 此处有两种情况：
                        // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                        // 该异常错误信息可在观察者中的onError（）中获得
                        return Observable.error(new Throwable("retryWhen"));
                        // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                        // return Observable.just(1);
                    }
                });
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "接收到了事件"+ value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "对Error事件作出响应" + e.toString());
                // 获取异常错误信息
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "对Complete事件作出响应");
            }
        });

    }

    private void scenesM8(){
        /**作用：无条件地、重复发送被观察者事件
         *repeat():无限次重复发送
         *repeatWhen(Integer count):有限次数发送
         */

        Observable.just(1, 2, 3, 4)
                .repeat(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e(TAG, "接收到了事件"+ value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    private void scenesM9(){
        /**
         *作用：有条件地、重复发送被观察者事件
         *原理：将原始Observable停止发送事件的标识（Complete()/Error()）转换成1个Object类型
         *     数据传递给1个新被观察者者，以此决定是否重新订阅&发送原来的Observable。
         *     1.若新被观察者（Observable）返回1个Complete/Error事件，则不重新订阅&发送原来的Observable
         *     2.若新被观察者（Observable）返回其余事件时，则重新订阅&发送原来的observable
         */

        Observable.just(1, 2, 4)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    // 在Function函数中，必须对输入的 Observable<Object>进行处理，这里我们使用的是flatMap操作符接收上游的数据
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                // Observable.empty() = 发送Complete事件，但不会回调观察者的onComplete（）
                                return Observable.empty();
                                // return Observable.error(new Throwable("不再重新订阅事件"));
                                // 返回Error事件 = 回调onError（）事件，并接收传过去的错误信息。

                                // return Observable.just(1);
                                // 仅仅是作为1个触发重新订阅被观察者的通知，发送的是什么数据并不重要，只要不是Complete（） /  Error（）事件
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应：" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     *作用：过滤/筛选被观察者发送的事件 & 观察者接收的事件
     *应用场景：
     *        1.根据指定条件过滤事件
     *        2.根据指定事件数量过滤事件
     *        3.根据指定时间过滤事件
     *        4.根据指定事件位置过滤事件
     */
    private void filterM1(){
        //过滤特定条件的事件
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

            }
        })
        .filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
                // a. 返回true，则继续发送
                // b. 返回false，则不发送（即过滤）
                return integer > 3;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "过滤后得到的事件是："+ value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void filterM2(){
        //过滤特定类型的数据
        Observable.just(1, "Canton", "fuck", 3)
                .ofType(Integer.class)//筛选出整型数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "获取到的整型事件元素是："+integer);
                    }
                });
    }

    private void filterM3(){
        //跳过某个事件

        //1.根据顺序跳过数据项
        Observable.just(1, 2, 3, 4)
                .skip(1)//跳过正序的前1项
                .skipLast(2)//跳过正序的后2项
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "获取到的整型事件元素是："+integer);
                        /**
                         *接收到：
                         *      1
                         *      2
                         */
                    }
                });

        //2.根据时间跳过数据项
        //发送事件特点：发送数据0-5.每隔1s发送一次，每次递增1；第1次发送延迟0s
        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                .skip(1, TimeUnit.SECONDS)//跳过第1s发送的数据
                .skipLast(1, TimeUnit.SECONDS)//跳过最好1s发送的数据
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "获取到的整型事件元素是："+aLong);
                        /**
                         *接收到：
                         *      1
                         *      2
                         *      3
                         */
                    }
                });

    }

    private void filterM4(){
        //过滤事件序列中重复的事件
        Observable.just(1, 2, 3, 1, 2)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "不重复的整型事件元素是："+integer);
                    }
                });

        //过滤事件序列中连续重复的事件
        Observable.just(1, 2, 3, 1, 2, 3, 3, 4, 4)
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "不连续重复的整型事件元素是："+integer);
                    }
                });

    }

    private void filterM5(){
        //指定观察者最多能接收到的事件数量
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        })
          .take(2)
          .subscribe(new Observer<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(Integer integer) {
                  Log.d(TAG, "过滤后得到的事件是："+  integer);
              }

              @Override
              public void onError(Throwable e) {
                  Log.d(TAG, "onError");
              }

              @Override
              public void onComplete() {
                  Log.d(TAG, "onComplete");
              }
          });

        //指定观察者只能接收被观察者发送的最后几个事件
        Observable.just(1, 2, 3, 4, 5)
                .takeLast(3)//指定观察者只能接收被观察者发送最后3个事件
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "过滤后得到的事件是："+  integer);
                    }
                });
    }

    private void filterM6(){
        //某段时间内只发送该段时间内第1次事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(500);

                emitter.onNext(2);
                Thread.sleep(400);

                emitter.onNext(3);
                Thread.sleep(300);

                emitter.onNext(4);
                Thread.sleep(300);

                emitter.onNext(5);
                Thread.sleep(300);

                emitter.onNext(6);
                Thread.sleep(400);

                emitter.onNext(7);
                Thread.sleep(300);

                emitter.onNext(8);
                Thread.sleep(300);

            }
        })
          .throttleFirst(1, TimeUnit.SECONDS)//每1s中采用数据
          .subscribe(new Observer<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(Integer value) {
                  Log.d(TAG, "接收到了事件"+ value );
              }

              @Override
              public void onError(Throwable e) {
                  Log.d(TAG, "对Error事件作出响应");
              }

              @Override
              public void onComplete() {
                  Log.d(TAG, "对Complete事件作出响应");
              }
          });
        /**
         *结果：
         *    1
         *    4
         *    7
         */

        //在某段时间内，只发送这段时间内最后1次事件
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(500);

                emitter.onNext(2);
                Thread.sleep(400);

                emitter.onNext(3);
                Thread.sleep(300);

                emitter.onNext(4);
                Thread.sleep(300);

                emitter.onNext(5);
                Thread.sleep(300);

                emitter.onNext(6);
                Thread.sleep(400);

                emitter.onNext(7);
                Thread.sleep(300);

                emitter.onNext(8);
                Thread.sleep(300);

                emitter.onNext(9);
                Thread.sleep(300);
            }
        })
          .throttleLatest(1, TimeUnit.SECONDS)
          .subscribe(new Observer<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(Integer integer) {

              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onComplete() {

              }
          });
        /**
         *结果：
         *    3
         *    6
         *    9
         */

    }

    private void filterM7(){
        /**
         *Sample()
         *作用：在某段时间内，只发送该段时间内最新（最后）1次事件，与throttleLast()操作符类似
         */
    }

    private void filterM8(){
        /**
         * throttleWithTimeout （） / debounce（）
         *发送数据事件时，若2次发送事件的间隔<指定时间，就会丢弃前一次的数据，保留后一次数据；
         *>=指定时间时，将发生上一次间隔保留的数据
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(500);//1和2之间的间隔小于指定时间1s，所以前1次数据（1）会被抛弃，2会被保留
                e.onNext(2);
                Thread.sleep(1500);//因为2和3之间的间隔大于指定时间1s，所以之前被保留的2事件将发出
                e.onNext(3);
                Thread.sleep(1500);//因为3和4之间的间隔大于指定时间1s，所以3事件将发出
                e.onNext(4);
                Thread.sleep(500);//因为4和5之间的间隔小于指定时间1s，所以前1次数据（4）会被抛弃，5会被保留
                e.onNext(5);
                Thread.sleep(500);//因为5和6之间的间隔小于指定时间1s，所以前1次数据（5）会被抛弃，6会被保留
                e.onNext(6);
                Thread.sleep(1500);//因为6和Complete()之间的间隔大于指定时间1s，所以之间被保留的6将发出
            }
        })
         .throttleWithTimeout(1, TimeUnit.SECONDS)
         .subscribe(new Observer<Integer>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onNext(Integer value) {
                 Log.d(TAG, "接收到了事件"+ value);
             }

             @Override
             public void onError(Throwable e) {
                 Log.d(TAG, "对Error事件作出响应");
             }

             @Override
             public void onComplete() {
                 Log.d(TAG, "对Complete事件作出响应");
             }
         });
        /**
         *结果：
         *    2
         *    3
         *    6
         *    对Complete事件作出响应
         */
    }

    /**
     *根据指定事件位置过滤事件
     *需求场景：通过设置指定的位置，过滤该位置的事件
     *
     */
    private void filterM9(){
        /**
         *仅选取第一个元素/第二个元素
         */
        Observable.just(1, 2, 3, 4, 5)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"获取到的第一个事件是： "+ integer);
                    }
                });
        /**
         *结果：
         *    1
         */

        Observable.just(1, 2, 3, 4, 5)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"获取到的第一个事件是： "+ integer);
                    }
                });
        /**
         *结果：
         *    5
         */
    }

    private void filterM10(){
        /**
         *指定接收某个元素（通过索引值确定）
         *注：允许越界，即获取的位置索引 > 发送事件序列长度
         */

        //获取位置索引=2的元素；位置索引从0开始
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"获取到的事件元素是： "+ integer);
                    }
                });
        /**
         *结果：
         *    3
         */

        //获取的位置索引>发送事件的序列长度时，设置默认参数
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(6, 10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"获取到的事件元素是： "+ integer);
                    }
                });
        /**
         *结果：
         *    10
         */
    }

    private void filterM11(){
        /**
         *在elementAt()的基础上，当出现越界情况（即获取的位置索引>发送事件序列长度）时，
         *即抛出异常
         */
        Observable.just(1, 2, 3, 4, 5)
                .elementAtOrError(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"获取到的事件元素是： "+ integer);
                    }
                });
        /**
         *结果：
         *    xxxxx异常
         */
    }


    /**
     *条件/布尔操作符
     */
    private void booleanM1(){
       /**
        *判断发送的每项数据是否都满足设置的函数条件
        *若满足，返回true；否则，返回false
        */
       Observable.just(1, 2, 3, 4, 5, 6)
               .all(new Predicate<Integer>() {
                   @Override
                   public boolean test(Integer integer) throws Exception {
                       //该函数用于判断observable发送的数据是否都满足该条件
                       return (integer <= 10);
                   }
               }).subscribe(new Consumer<Boolean>() {
           @Override
           public void accept(Boolean aBoolean) throws Exception {
               Log.d(TAG,"result is "+ aBoolean);
           }
       });
       /**
        *所有数据都满足条件，结果：
        *                     result is true
        */
    }

    private void booleanM2(){
        /**
         *判断发送的每项数据是否满足设置的函数条件
         *满足该条件，则发送该项数据；否则不发送
         */
        Observable.interval(1, TimeUnit.SECONDS)//每1s发送1个数据 = 从0开始，递增1
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        //满足该条件时才发送Observable的数据
                        return (aLong < 3);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG,"发送了事件 "+ aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void booleanM3(){
        /**
         *判断发送的每项数据是否满足设置函数条件
         *直到该判断条件 = false时，才开始发送Observable数据
         */
        Observable.interval(1, TimeUnit.SECONDS)
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        //直到判断条件不成立 = false = 发射数据>=5，才开始发送数据
                        return (aLong < 5);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG,"接收到了事件 "+ value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void booleanM4(){
        /**
         *执行到某个条件时，停止发送事件
         */
        Observable.interval(1, TimeUnit.SECONDS)
                //通过takeUntil的Predicate传入判断条件
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        //当返回true时，停止发送事件；当发送数据满足>5时，就停止发送数据
                        return (aLong > 5);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG,"接收到了事件 "+ value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /**
         *判断条件也可以是observable，即等到takeUntil()传入的observable开始发送数据
         *（原始）第一个observable的数据停止发送数据
         */
        Observable.interval(1, TimeUnit.SECONDS)
                //第2个observable：延迟5s后开始发送1个Long型数据
                .takeUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long value) throws Exception {
                        Log.d(TAG,"接收到了事件 "+ value);
                    }
                });
    }

    private void booleanM5(){
        /**
         *等到skipUntil()传入的observable开始发送数据，（原始）第一个observable的数据才开始发送
         */
        //(原始)第1个observable：每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long value) throws Exception {
                        Log.d(TAG, "接收到了事件"+ value);
                    }
                });

    }

    private void booleanM6(){
        /**
         *判断2个observable需要发送的数据是否相同
         *若相同，返回true；否则，返回false
         */
        Observable.sequenceEqual(Observable.just(4, 5, 6), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG,"2个Observable是否相同："+ aBoolean);
                    }
                });
    }

    private void booleanM7(){
        /**
         *判断发送的数据中是否包含指定数据
         *1.若包含，返回true；否则，返回false
         *2.内部实现 = exists()
         */
        Observable.just(1, 2, 3, 4, 5, 6)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG,"result is "+ aBoolean);
                    }
                });
    }

    private void booleanM8(){
        /**
         *判断发送的数据是否为空
         *若为空，返回true；否则，返回false
         */
        rx.Observable.just(1, 2, 3)
                .isEmpty()//判断发送的数据是否为空
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        Log.d(TAG,"result is "+ aBoolean);
                    }
                });
    }

    private void  booleanM9(){
        /**
         *当需要发送多个observable时，只发送先发送数据的observable的数据，而其余
         *observable则丢弃
         */
        List<ObservableSource<Integer>> list = new ArrayList<>();
        //延迟1s发送数据
        list.add(Observable.just(1, 2, 3).delay(1, TimeUnit.SECONDS));
        //正常发送数据
        list.add(Observable.just(4, 5, 6));

        /**
         *一共需要发送2个observable的数据
         *由于amb()，所有仅发送先发送数据的observable
         *即第二个（第一个延时了）
         */
        Observable.amb(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "接收到了事件 "+integer);
            }
        });

    }

    private void booleanM10(){
        /**
         *在不发送任何有效事件（next事件）、仅发送了complete事件的前提下，发送一个默认值
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onComplete();
            }
        })
          .defaultIfEmpty(10)//默认值为10
          .subscribe(new Observer<Integer>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(Integer value) {
                  Log.d(TAG, "接收到了事件"+ value);
              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onComplete() {
                  Log.d(TAG, "对Complete事件作出响应");
              }
          });
    }

}
