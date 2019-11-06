package cn.liuzehao.dagger2.daggerPraOne.bean;

import javax.inject.Inject;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class Two {
    private final String NAME = "liuxiaosheng";
    @Inject
    public Two(){
    }

    public String getName(){
        return NAME;
    }
}
