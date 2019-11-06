package cn.liuzehao.dagger2.daggerPraOne.three;

import javax.inject.Named;

import cn.liuzehao.dagger2.daggerPraOne.OneActivity;
import cn.liuzehao.dagger2.daggerPraOne.bean.Five;
import cn.liuzehao.dagger2.daggerPraOne.bean.Four;
import dagger.Module;
import dagger.Provides;

/**
 * Created by liuzehao on 2019/9/7.
 */
@Module
public class FourModule {
    private OneActivity oneActivity;

    public FourModule(OneActivity oneActivity) {
        this.oneActivity = oneActivity;
    }

    @Named("liuzehao")
    @Provides
    Four provideFourOne(Five five, String flag){
        return new Four(five, oneActivity, "liuzehao");
    }

    @Named("liuxiaosheng")
    @Provides
    Four provideFourTwo(Five five, String flag){
        return new Four(five, oneActivity, "liuxiaosheng");
    }
}
