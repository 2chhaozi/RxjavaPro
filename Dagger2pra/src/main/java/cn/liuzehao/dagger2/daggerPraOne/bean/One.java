package cn.liuzehao.dagger2.daggerPraOne.bean;

import javax.inject.Inject;

/**
 * Created by liuzehao on 2019/9/7.
 */
public class One {
    private final String NAME = "liuzehao";
    Two two;
    @Inject
    public One(Two two){
        this.two = two;
    }

    public String getName() {
        return (two == null ? NAME : two.getName());
    }
}
