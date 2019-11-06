package cn.liuzehao.dagger2.daggerPraOne.bean;

import javax.inject.Inject;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class Five {
    private String flag;

    @Inject
    public Five(){

    }

    public String getFlag(){
        return flag;
    }
}
