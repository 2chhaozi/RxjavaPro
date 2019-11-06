package cn.liuzehao.rxjavapro.RetrofitBaseUse;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by liuzehao on 2019/7/31.
 */
public interface GetRequest_Interface {

    //第一类：网络请求方法

    /*@Get注解的作用：采用Get方式请求数据
    getCall() = 接收网络请求数据的方法
    其中返回类型为Call<*>,*为接收返回数据的类，比如ResponseBody*/
    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<ResponseBody> getCall();

    //Retrofit将网络请求的URL分成2部分；
    //第一部分:http://fanyi.youdao.com/
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") //设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
            .build();


    /*具体URL整合规则如下：
    *
    *        类型                    具体使用
    *
    * path = 完整的URL            URL = "http://host:port/aa/apath"
    *                            其中path = "http://host:port/aa/apath"
    *                            baseUrl = 不设置
    *
    *path = 绝对路径             URL = "http://host:port/apath"
    *                           其中path = "/apath"
    *                           baseUrl = "http://host:port/a/b"
    *
    *path = 相对路径             URL = "http://host:port/a/b/apath"
    *baseUrl = 目录形式          其中path = "apath"
    *                           baseUrl = "http://host:port/a/b/"
    *
    *path = 相对路径             URL = "http://host:port/a/apath"
    *baseUrl = 文件形式          其中path = "apath"
    *                           baseUrl = "http://host:port/a/b/"
    *
    * 一般采用第三种
    * */




    /*
    * @HTTP作用：替换@GET、@POST、@PUT、@DELETE、@HEAD注解的作用 及 更多功能拓展
    *具体使用：通过属性method、path、hasBody进行设置
    * */

    /*method：网络请求的方法（区分大小写）
    *path：网络请求地址路径
    *hasBody：是否有请求体
    *{id}是一个变量
    * */
    @HTTP(method = "GET",path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getMyCall(@Path("id") int id);



    //-------------------------------------------------------------------


    //第二类：标记

    /*@FormUrlEncoded作用：表示发送form-encoded的数据，表示请求体是一个表单
    * 每个键值对需要用@Field来注解键名，随后对象需要提供值
    * （"userName"）表示将后面的Stirng userName作为该键对应的值
    * */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded1(@Field("userName") String userName, @Field("age") int age);

    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

    /*@Multipart作用：表示发送form-encoded的数据（适用于有文件上传的场景）
    * 每个键值对需要用@Part来注解键名，随后的对象需要提供值
    * 下例中@Part后面支持三种类型，包括：RequestBody、okhttp3.MultipartBody.Part、任意类型
    * 除okhttp3.MultipartBody.Part外其他类型都必须带上表单字段
    * */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name")RequestBody name,
                                     @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，
     * 如后面会介绍的 使用{@link com.google.gson.Gson} 的
     * {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload3(@PartMap Map<String, RequestBody> args);
    //具体使用--->RetrofitActivity--->test1


    //-------------------------------------------------------------------

    //第三类：网络请求参数

    /*@Header & @Headers
    * 作用：添加请求头 & 添加不固定的请求头
    * 以下的效果是一致的。
    * 区别在于使用场景和使用方式
    * 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
    * 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法
    * */
    class User{

    }
    @GET("user")
    Call<User> getUser1(@Header("Authorization") String authorization);

    @Headers("Authorization: authorization")
    @GET("user")
    Call<User> getUser2();

    /*@body定义：用于非表单请求体
    * 作用：已Post方式传递自定义类型数据给服务器
    * 特别注意：如果提交的是一个Map，则相当于@Field
    * 不过Map要经过FormBody.Builder类处理成为符合Okhttp格式的表单，如--->RetrofitActivity--->test2：
    * */

    /*@Field & FieldMap
    * 作用：发送Post请求时，提交请求的表单字段
    * */

    /*@Query和QueryMap
    * 作用：用于@Get方法的查询参数（Query = Url中‘ ？’ 后面的key-value）
    * 如：url = http://www.println.net/?cate=android，其中，Query = cate
    * */
    @GET("/")
    Call<String> cate(@Query("cate") String cate);

    /*@Path
    * 作用：URL地址的缺省值
    * 在发起请求时， {user} 会被替换为方法的第一个参数 user（被@Path注解作用）
    * */
    @GET("users/{user}/respos")
    Call<ResponseBody> getCall5(@Path("user") String user);

    /*@Url
    * 作用：直接传入一个请求的Url变量，用于URL设置
    * 当有URL注解时，@GET传入的URL就可以省略
    * 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
    * */
    @GET
    Call<ResponseBody> getCall6(@Url String url, @Query("showAll") boolean isShowAll);
}
