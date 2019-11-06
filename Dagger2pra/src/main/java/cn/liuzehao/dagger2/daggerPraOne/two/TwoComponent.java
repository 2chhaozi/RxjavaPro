package cn.liuzehao.dagger2.daggerPraOne.two;

import cn.liuzehao.dagger2.daggerPraOne.OneActivity;
import dagger.Component;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Component(modules = {TwoModule.class})
public interface TwoComponent {
    void inject(OneActivity oneActivity);
}
