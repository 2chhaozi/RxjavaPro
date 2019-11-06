package cn.liuzehao.rxjavapro.rxPracticeSix;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by liuzehao on 2019/8/30.
 */
public interface GetRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();
}
