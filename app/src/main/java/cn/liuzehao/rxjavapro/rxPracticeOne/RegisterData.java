package cn.liuzehao.rxjavapro.rxPracticeOne;

import android.util.Log;

/**
 * Created by liuzehao on 2019/8/14.
 */
public class RegisterData {
    private int status;
    private content content;
    private static class content{
        private String from;
        private String to;
        private String ventor;
        private String out;
        private int errNo;
    }

    public void show(){
        Log.e("liuzehao", "RegisterData//翻译内容 = " + content.out);
    }
}
