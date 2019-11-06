package cn.liuzehao.dagger2.daggerPraOne.three;

import cn.liuzehao.dagger2.daggerPraOne.bean.Five;
import dagger.Module;
import dagger.Provides;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Module
public class FiveModule {

    @Provides
    Five providesFive(){
        return new Five();
    }
}
