package cn.liuzehao.rxjavapro.rxPracticeFive;

import android.util.Log;

/**
 * Created by liuzehao on 2019/8/28.
 */
public class Translation {
    private int status;
    private content content;
    private static class content{
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public void show(){
        Log.e("liuzehao", content.out);
    }
}
