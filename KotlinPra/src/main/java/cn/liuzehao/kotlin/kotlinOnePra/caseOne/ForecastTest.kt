package cn.liuzehao.kotlin.kotlinOnePra.caseOne

import java.util.*

/**
 * Created by liuzehao on 2019-09-12.
 */
/**
 *数据类可以让你避免创建Java中的用于保存状态但又操作非常简单的POJO的模板代码
 *通常只提供用于访问他们属性的简单的getter和setter
 */
data class ForecastTest(val date: Date, val temperature: Float, val details: String) {

}