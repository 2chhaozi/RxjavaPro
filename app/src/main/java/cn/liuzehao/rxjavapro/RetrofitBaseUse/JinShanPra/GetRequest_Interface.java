package cn.liuzehao.rxjavapro.RetrofitBaseUse.JinShanPra;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by liuzehao on 2019/8/5.
 */
public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();
}
