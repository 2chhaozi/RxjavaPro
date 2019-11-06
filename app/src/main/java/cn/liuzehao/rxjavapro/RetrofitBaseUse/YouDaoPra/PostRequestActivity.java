package cn.liuzehao.rxjavapro.RetrofitBaseUse.YouDaoPra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liuzehao on 2019/8/5.
 */
public class PostRequestActivity extends AppCompatActivity {
    private static final String TAG = "PostRequestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postRequest();
    }

    private void postRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);
        Call<TranslationY> call = request.getCall("I want be rich");
        call.enqueue(new Callback<TranslationY>() {
            @Override
            public void onResponse(Call<TranslationY> call, Response<TranslationY> response) {
                Log.e(TAG, "onResponse//"+response.toString());
            }

            @Override
            public void onFailure(Call<TranslationY> call, Throwable t) {
                Log.e(TAG, "onFailure//"+t.getMessage());
            }
        });
    }
}
