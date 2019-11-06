package cn.liuzehao.kotlin.kotlinOnePra

import android.app.Application

/**
 * Created by liuzehao on 2019-09-17.
 */
class App : Application(){
    companion object{
        /*private var instance: Application? = null
        fun instance() = instance!!*/

        /**
         *使用noNull委托。它会含有一个可null的变量并会在我们设置这个属性的时候分配一个真实的值。
         *如果这个值在被获取之前没有被分配，它就会抛出异常
         */
        //var instance: App by Delegates.notNull()


        //报错了，之后解决
        //var instance: App by DelegatesExt.notNullSingleValue()

    }

    override fun onCreate() {
        super.onCreate()
    }
}