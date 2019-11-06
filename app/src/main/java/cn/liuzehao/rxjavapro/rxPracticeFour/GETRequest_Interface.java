package cn.liuzehao.rxjavapro.rxPracticeFour;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by liuzehao on 2019/8/23.
 */
public interface GETRequest_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation1> getCall1();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    Observable<Translation2> getCall2();
}
