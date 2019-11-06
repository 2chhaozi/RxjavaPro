package cn.liuzehao.lambda.lambdaOne;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import cn.liuzehao.lambda.R;

/**
 * Created by liuzehao on 2019-09-10.
 */
public class LambadOneActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";

    private Button lambdaOneBtn;
    private Button lambdaTwoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lambdaone_layout);
        lambdaOneBtn = findViewById(R.id.lambda_one_btn);
        lambdaTwoBtn = findViewById(R.id.lambda_two_btn);
    }

    private void lambdaBtn(){
        lambdaOneBtn.setOnClickListener(v -> Toast.makeText(LambadOneActivity.this, "Lambda按钮点击", Toast.LENGTH_SHORT));
    }

    private void lambdaOne(){
        //用（）替代整个匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "普通表达式！");
            }
        }).start();

        new Thread(() -> Log.e(TAG, "lambda匿名内部类替换成功")).start();
    }

    private void lambdaTwo(){
        List<String> languages = Arrays.asList("java", "scala", "python");
        //before java8
        for(String each : languages){
            Log.e(TAG, "普通打印："+each);
        }
        //after java8
        languages.forEach(s -> Log.e(TAG, "lambda表达式打印：" + s));
        /**
         *同样是替代匿名内部类，只是匿名内部类中的方法有参数s，代表着集合传递下来的单个字符串，所以
         *不能直接用（）表示，用一个自定义变量s表示
         */
    }

    private void lambdaThree(){
        /**
         *map将传递过来的事件转换成另一个事件，同时reduce将接收到的被转换的事件和原事件进行合并，最后将最终事件返回
         *内部原理是观察者模式
         */
        List<Double> cost = Arrays.asList(10.0, 20.0, 30.0);
        double allCost = cost.stream().map(x -> x + x * 0.05).reduce((sum, x) -> sum + x).get();
        Log.e(TAG, "lambda表达式函数式编程打印：" + allCost);
    }

    private void lambdaFour(){
        List<String> languages = Arrays.asList("Java","Python","scala","Shell","R");
        filterTest(languages,x -> x.startsWith("J"));
    }

    private void filterTest(List<String> languages, Predicate<String> condition){
        /**
         *将集合传过来的 数据过滤并打印
         */
        languages.stream().filter(x -> condition.test(x)).forEach(x -> Log.e(TAG, "lambda表达式函数式编程打印:" + x));
    }
}
