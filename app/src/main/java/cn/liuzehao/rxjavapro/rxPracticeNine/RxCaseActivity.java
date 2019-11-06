package cn.liuzehao.rxjavapro.rxPracticeNine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzehao on 2019/9/5.
 */
public class RxCaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     *遍历文件夹获取.png图片文件并加载
     */
    private void case1(){
        File[] foldes = new File[50];
        Observable.fromArray(foldes)
                .flatMap(new Function<File, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        return Observable.fromArray(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {

                    @Override
                    public boolean test(File file) throws Exception {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Function<File, Bitmap>() {

                    @Override
                    public Bitmap apply(File file) throws Exception {
                        return null;//将file文件转换成bitmap（事件转换）
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {

                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        //在主线程中进行UI设置
                    }
                });
    }

    /**
     *Subscriber和Observer的区别：
     *   1.onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，
     *   可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。
     *   需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
     *   onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。
     *   要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
     *
     *  2.unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。
     *  在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed()
     *  先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
     *  Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。
     *  所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，
     *  以避免内存泄露的发生。
     */

    private void case2(){
        List<Student> students = new ArrayList<>();
        List<String> courses = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Student student = new Student(i + "", i);
            courses.clear();
            courses.add("数学");
            courses.add("语文");
            courses.add("英语");
            student.setCourse(courses);
            students.add(student);
        }
        Flowable.fromIterable(students)
                .map(new Function<Student, List<String>>() {

                    @Override
                    public List<String> apply(Student student) throws Exception {
                        return student.getCourse();
                    }
                })
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> courses) throws Exception {

                    }
                });


    }

    /**
     *Observable.doOnSubscribe()是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。
     *默认情况下，doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，
     *它将执行在离它最近的 subscribeOn() 所指定的线程
     */
    private void case3(){
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

            }
        })
          .subscribeOn(Schedulers.io())
          .doOnSubscribe(new Consumer<Disposable>() {
              @Override
              public void accept(Disposable disposable) throws Exception {
                  //progressBar.setVisibility(View.VISIBLE); //需要在主线程执行，执行在离它最近的subscribeOn()所指定的线程
              }
          })
          .subscribeOn(AndroidSchedulers.mainThread())//指定主线程
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {

              }
          });


    }

    /**
     *doOnNext()等，在事件发送到观察者前将事件拦截处理
     */
}
