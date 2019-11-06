package cn.liuzehao.dagger2.daggerPraOne.one;

import cn.liuzehao.dagger2.daggerPraOne.bean.One;
import cn.liuzehao.dagger2.daggerPraOne.bean.Two;
import dagger.Module;
import dagger.Provides;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Module
public class OneModule {

    /**
     *构造方法需要其他参数的时候
     */
    @Provides
    Two providesTwo(){
        return new Two();
    }

    @Provides
    One providerOne(Two two){
        return new One(two);
    }
}
