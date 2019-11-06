package cn.liuzehao.rxjavapro.rxPracticeFour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.liuzehao.rxjavapro.RetrofitBaseUse.JinShanPra.Translation;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by liuzehao on 2019/8/23.
 */
public class MergeShowActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";
    private String result = "数据源来自：";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //获取合并本地网络数据
    private void mergeRequest(){
        Observable<String> netRequest = Observable.just("网络获取数据");
        Observable<String> localRequest = Observable.just("本地获取数据");

        Observable.merge(netRequest, localRequest)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        Log.e(TAG, "数据来源有：" + o);
                        result += o;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "数据获取完成");
                    }
                });
    }

    //获取合并2个服务器网络数据
    private void zipRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GETRequest_Interface getRequest = retrofit.create(GETRequest_Interface.class);
        Observable<Translation1> net1Request = getRequest.getCall1();
        Observable<Translation2> net2Request = getRequest.getCall2();
        
        Observable.zip(net1Request, net2Request, new BiFunction<Translation1, Translation2, String>() {

            //第三个参数：合并后数据的数据类型
            @Override
            public String apply(Translation1 translation1, Translation2 translation2) throws Exception {

                return translation1.show() + "&" + translation2.show();
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "最终接收到的数据是："+s);
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
