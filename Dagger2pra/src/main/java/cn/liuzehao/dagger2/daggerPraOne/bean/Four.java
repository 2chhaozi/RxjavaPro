package cn.liuzehao.dagger2.daggerPraOne.bean;

import javax.inject.Inject;

import cn.liuzehao.dagger2.daggerPraOne.OneActivity;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class Four {
    private Five five;
    private OneActivity oneActivity;
    private String flag;

    @Inject
    public Four(Five five, OneActivity oneActivity, String flag) {
        this.five = five;
        this.oneActivity = oneActivity;
        this.flag = flag;
    }

    public String getFlag(){
        return flag;
    }
}
