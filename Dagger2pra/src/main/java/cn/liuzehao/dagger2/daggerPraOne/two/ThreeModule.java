package cn.liuzehao.dagger2.daggerPraOne.two;

import cn.liuzehao.dagger2.daggerPraOne.bean.Three;
import cn.liuzehao.dagger2.daggerPraOne.bean.Two;
import dagger.Module;
import dagger.Provides;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Module
public class ThreeModule {
    @Provides
    Three providersThree(){
        return new Three();
    }
}
