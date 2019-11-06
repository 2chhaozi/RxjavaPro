package cn.liuzehao.rxjavapro.rxPracticeOne;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by liuzehao on 2019/8/14.
 */
public interface GetRequest_Interface {

    //注册请求
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<RegisterData> getRegister();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<LoginData> getLogin();
}
