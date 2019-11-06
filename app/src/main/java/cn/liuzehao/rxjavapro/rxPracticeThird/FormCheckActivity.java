package cn.liuzehao.rxjavapro.rxPracticeThird;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;


import com.jakewharton.rxbinding2.widget.RxTextSwitcher;
import com.jakewharton.rxbinding2.widget.RxTextView;

import cn.liuzehao.rxjavapro.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * Created by liuzehao on 2019/8/22.
 */
public class FormCheckActivity extends AppCompatActivity {
    private EditText nameEt;
    private EditText ageEt;
    private EditText jobEt;
    private Button commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.formchecklayout);

        nameEt = findViewById(R.id.name_et);
        ageEt = findViewById(R.id.age_et);
        jobEt = findViewById(R.id.job_et);
        commit = findViewById(R.id.commit);

        /**
         *为每个EditText设置被观察者，用于发送监听事件
         *说明：(需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0')
         *     1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher）
         *     2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         *     3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         */
        Observable<CharSequence> nameObservable = RxTextView.textChanges(nameEt).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(ageEt).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(jobEt).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                boolean isNameEmpty = !TextUtils.isEmpty(nameEt.getText().toString());
                boolean isAgeEmpty = !TextUtils.isEmpty(ageEt.getText().toString());
                boolean isJobEmpty = !TextUtils.isEmpty(jobEt.getText().toString());
                //返回信息 = 联合判断，即3个信息同时已填写，“提交按钮”才可点击
                return isNameEmpty && isAgeEmpty && isJobEmpty;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isCommit) throws Exception {
                if(isCommit){
                    commit.setEnabled(true);
                }else{
                    commit.setEnabled(false);
                }
            }
        });
    }
}
