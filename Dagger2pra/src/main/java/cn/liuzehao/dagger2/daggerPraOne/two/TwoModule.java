package cn.liuzehao.dagger2.daggerPraOne.two;

import cn.liuzehao.dagger2.daggerPraOne.bean.Three;
import cn.liuzehao.dagger2.daggerPraOne.bean.Two;
import dagger.Module;
import dagger.Provides;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Module(includes = {ThreeModule.class})
public class TwoModule {
    /**
     * Dagger会现在A moudule 中寻找对象，如果没找到，会去找module B 中是否有被Inject注解的对象，
     * 如果还是没有，则抛出异常
     */
}
