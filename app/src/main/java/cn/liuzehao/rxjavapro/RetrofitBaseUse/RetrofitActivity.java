package cn.liuzehao.rxjavapro.RetrofitBaseUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

/**
 * Created by liuzehao on 2019/8/5.
 */
public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Retrofit支持多种数据解析方式，对应解析方式需要对应的依赖
         * Gson
         * com.squareup.retrofit2:converter-gson:2.0.2
         *
         * Jackson
         * com.squareup.retrofit2:converter-jackson:2.0.2
         *
         * Simple XML
         * com.squareup.retrofit2:converter-simplexml:2.0.2
         *
         * Protobuf
         * com.squareup.retrofit2:converter-protobuf:2.0.2

         * Moshi
         * com.squareup.retrofit2:converter-moshi:2.0.2
         *
         * Wire
         * com.squareup.retrofit2:converter-wire:2.0.2

         * Scalars
         * com.squareup.retrofit2:converter-scalars:2.0.2
        * */

        /*Retrofit支持多种网络请求适配器方式：guava、Java8和rxjava
        * 使用时如使用的是 Android 默认的 CallAdapter，则不需要添加网络请求适配器的依赖，否则则需要按照需求进行添加
        *
        * guava
        * com.squareup.retrofit2:adapter-guava:2.0.2
        *
        * Java8
        * com.squareup.retrofit2:adapter-java8:2.0.2
        *
        * rxjava
        * com.squareup.retrofit2:adapter-rxjava:2.0.2
        * */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())// 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();

        //test1 创建网络请求接口实例
        GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
        //@FormUrlEncoded
        service.testFormUrlEncoded1("liuzehao", 26);
        //@Multipart
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(mediaType, "liuzehao");
        RequestBody age = RequestBody.create(mediaType, "26");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "test.text", file);
        Call<ResponseBody> call1 = service.testFileUpload1(name, age, part);

        Map<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        Call<ResponseBody> call2 = service.testFileUpload2(map, part);

        //test2
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key", "value");
        FormBody body = builder.build();

        //test3 对发送请求进行封装
        Call<ResponseBody> call = service.getCall();
        //发送网络请求（异步）
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //请求处理，输出结果
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        //发送网络请求（同步）
        try {
            Response<ResponseBody> call3 = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
