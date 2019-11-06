package cn.liuzehao.rxjavapro.RetrofitBaseUse.JinShanPra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.liuzehao.rxjavapro.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuzehao on 2019/8/5.
 */
public class GetRequestActivity extends AppCompatActivity {
    private static final String TAG = "GetRequestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getrequestlayout);
        getRequest();
    }

    private void getRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface getRequest = retrofit.create(GetRequest_Interface.class);
        Call<Translation> call = getRequest.getCall();
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Log.e(TAG, "onResponse//"+response.body().show());
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.e(TAG, "onFailure//"+t.getMessage());
            }
        });
    }
}
