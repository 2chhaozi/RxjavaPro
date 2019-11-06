package cn.liuzehao.kotlin.kotlinOnePra.caseFour

import android.util.Log

/**
 * Created by liuzehao on 2019-11-04.
 */

/**
 *扩展函数，给Kotlin原生集合类添加一个扩展函数
 */
fun MutableList<Int>.swap(index1: Int, index2: Int){
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun Parent.printResult(){
    Log.e(TAG, "${mValue1} + ${mValue2} = ${add()}")
}

fun Child.printResult(){
    Log.e(TAG, "${mValue1} - ${mValue2} = ${sub()}")
}

fun MyClass.newInstance(value: Int): MyClass{
    return MyClass()
}

/**
 *扩展属性，需要实现setter部分
 *由于扩展属性没有backing field字段，因此保存和获取属性值需要使用一个类成员变量
 *但成员变量必须声明为public
 */
var MyClass.value: Int
    get() = mValue
    set(value) {
        mValue = value
    }