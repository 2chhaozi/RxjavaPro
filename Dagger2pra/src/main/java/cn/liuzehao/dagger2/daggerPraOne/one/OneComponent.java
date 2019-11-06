package cn.liuzehao.dagger2.daggerPraOne.one;

import cn.liuzehao.dagger2.daggerPraOne.OneActivity;
import dagger.Component;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Component(modules = {OneModule.class})
public interface OneComponent {

    void inject(OneActivity daggerOneActivity);
}
