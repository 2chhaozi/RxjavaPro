package cn.liuzehao.rxjavapro.rxPracticeTwo;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by liuzehao on 2019/8/22.
 */
public class GetCacheActivity extends AppCompatActivity {
    private static final String TAG= "liuzehao";
    private String memoryCache = null;
    private String diskCache = "磁盘有缓存";
    private String netCache = "请求网络数据";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable<String> requestMemory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if(!TextUtils.isEmpty(memoryCache)){
                    emitter.onNext("请求内存数据");
                }else{
                    emitter.onComplete();
                }
            }
        });

        Observable<String> requestDisk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if(!TextUtils.isEmpty(diskCache)){
                    emitter.onNext("请求磁盘数据");
                }else{
                    emitter.onComplete();
                }
            }
        });

        Observable<String> requestNet = Observable.just("请求网络数据");

        Observable.concat(requestMemory, requestDisk, requestNet)
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e(TAG, "=======>"+o);
                    }
                });

    }



}
