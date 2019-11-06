package cn.liuzehao.rxjavapro.rxPracticeOne;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.liuzehao.rxjavapro.R;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuzehao on 2019/8/13.
 */
public class RegisterALoginAvctivity extends AppCompatActivity {

    private static final String TAG = "RegisterALoginAvctivity";
    Observable<RegisterData> registerObserverable;
    Observable<LoginData> loginObservable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getrequestlayout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface getRequest = retrofit.create(GetRequest_Interface.class);

        registerObserverable = getRequest.getRegister();
        loginObservable = getRequest.getLogin();

        registerObserverable.subscribeOn(Schedulers.io())   // （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
                .doOnNext(new Consumer<RegisterData>() {
                    @Override
                    public void accept(RegisterData result) throws Exception {
                        Log.d(TAG, "注册请求成功");
                        result.show();
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                })

                .observeOn(Schedulers.io())   // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<RegisterData, ObservableSource<LoginData>>() { // 作变换，即作嵌套网络请求
                    @Override
                    public ObservableSource<LoginData> apply(RegisterData result) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
                        return loginObservable;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
                .subscribe(new Consumer<LoginData>() {
                    @Override
                    public void accept(LoginData result) throws Exception {
                        Log.d(TAG, "登录请求成功");
                        result.show();
                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "登录失败");
                    }
                });
    }
}
