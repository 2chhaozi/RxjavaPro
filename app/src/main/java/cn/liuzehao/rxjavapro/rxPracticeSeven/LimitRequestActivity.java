package cn.liuzehao.rxjavapro.rxPracticeSeven;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import cn.liuzehao.rxjavapro.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by liuzehao on 2019/9/2.
 */
public class LimitRequestActivity extends AppCompatActivity {
    private static final String TAG = "liuzehao";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.limitrequestlayout);

        Button request = findViewById(R.id.request);

        //功能防抖：在2s内，无论点击多少次，也只会发送1次网络请求
        RxView.clicks(request)
                .throttleLast(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "发送了网络请求" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
