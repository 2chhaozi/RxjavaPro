package cn.liuzehao.dagger2.daggerPraOne.three;

import cn.liuzehao.dagger2.daggerPraOne.OneActivity;
import dagger.Component;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Component(modules = {FourModule.class, FiveModule.class})
public interface FourComponent {
    void inject(OneActivity oneActivity);
}
