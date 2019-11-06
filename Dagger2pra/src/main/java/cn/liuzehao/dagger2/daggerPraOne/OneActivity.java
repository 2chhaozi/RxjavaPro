package cn.liuzehao.dagger2.daggerPraOne;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Named;

import cn.liuzehao.dagger2.R;
import cn.liuzehao.dagger2.daggerPraOne.bean.Four;
import cn.liuzehao.dagger2.daggerPraOne.bean.One;
import cn.liuzehao.dagger2.daggerPraOne.bean.Three;
import cn.liuzehao.dagger2.daggerPraOne.bean.Two;
//import cn.liuzehao.dagger2.daggerPraOne.one.DaggerOneComponent;
//import cn.liuzehao.dagger2.daggerPraOne.two.DaggerTwoComponent;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class OneActivity extends AppCompatActivity {

    private static final String TAG = "liuzehao";

    @Inject
    One daggerOne;
    @Inject
    Three daggerThree;

    @Named("liuzehao")
    @Inject
    Four daggerFour_One;

    @Named("liuxiaosheng")
    @Inject
    Four daggerFour_Two;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_layout);

        /**
         *第一步：添加依赖关系
         */
        //方式一：
        //DaggerOneComponent.create().inject(this);

        //DaggerTwoComponent.create().inject(this);

        //方式二：
        //DaggerOneComponent.builder().build().inject(this);

        findViewById(R.id.one_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "daggerOne//"+(daggerOne == null ? "null" : daggerOne.getName()));
            }
        });

        findViewById(R.id.two_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "daggerTwo//"+(daggerThree == null ? "null" : daggerThree.getName()));
            }
        });

        findViewById(R.id.four_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "daggerFour//"+(daggerFour_One == null ? "One_null" : daggerFour_One)
                + "//" + (daggerFour_Two == null ? "Two_null" : daggerFour_Two));
            }
        });
    }
}
