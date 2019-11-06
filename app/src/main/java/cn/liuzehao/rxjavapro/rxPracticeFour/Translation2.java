package cn.liuzehao.rxjavapro.rxPracticeFour;

import android.util.Log;

/**
 * Created by liuzehao on 2019/8/23.
 */
public class Translation2 {
    private int status;
    private Content content;
    private static class Content{
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    public String show(){
        Log.e("liuzehao", "Translation2//show//"+content.out);
        return content.out;
    }
}
