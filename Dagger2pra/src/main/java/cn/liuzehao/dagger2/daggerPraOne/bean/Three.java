package cn.liuzehao.dagger2.daggerPraOne.bean;

import javax.inject.Inject;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class Three {
    private final String NAME = "liuhaorang";
    @Inject
    public Three(){
    }

    public String getName(){
        return NAME;
    }
}
