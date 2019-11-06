package cn.liuzehao.kotlin.kotlinOnePra.caseFour

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * Created by liuzehao on 2019-11-04.
 */
class FourActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}

val TAG = "liuzehao"

fun enum(){
    var dircetion1: Direction ?= null
    var direction2: Direction = Direction.NORTH
    var direction3 = Direction.EAST
    var direction4 = Direction.EAST
    if(direction3 == direction4){
        Log.e(TAG, "枚举类型值相等")
    }else{
        Log.e(TAG, "枚举类型值不相等")
    }
    //默认状态下，直接输出枚举类型的元素值，会输出元素值名称
    Log.e(TAG, "" + direction2)
    //获取枚举值的名称或索引
    Log.e(TAG, "" + direction2.name + "//" + direction2.ordinal)
    //获取枚举值对应的数值
    Log.e(TAG, "" + Job.valueOf("TEACHER"))
    for(d in Job.values()){
        Log.e(TAG, "" + d)
    }
}

enum class Direction{
    NORTH, SOUNTH, WEST, EAST
}

enum class Job private constructor(val d: Int){
    TEACHER(1), DOCTOR(2), SOLDER(3), WRITTER(4);

    override fun toString(): String {
        return d.toString()
    }
}

/**
 *扩展函数
 */
fun expand1(){
    val mutableList = mutableListOf(1, 2, 3)
    mutableList.swap(0, 2)
    Log.e(TAG, "expand1:" + mutableList)

    /**
     *尽管 parent2 的实例是 Child ，但由于通过扩展向 Child 添加的
     *printResult 方法并没有重写 Parent.printResult 方法，因此 parent2.printResult 仍然调用的是
     *Parent. printResult 方法 而且，因为 open 不能用在顶层函数中，所以通过扩展是不能添加可继
     *承的成员函数的 (Kotlin 默认不允许继承）
     */
    var parent1: Parent = Parent(1, 2)
    var parent2: Parent = Child(1, 2)
    parent1.printResult()//输出1+2 = 3
    parent2.printResult()//输出1+2 = 3


    var myClass = MyClass()
    myClass.str = "hello"
    myClass.value = 400 //设置扩展属性的值
    Log.e(TAG, "str: " + myClass.str + "//" + "value: " + myClass.value)

    //在类中使用扩展
    C().caller(D())

}

open class Parent(val value1:Int, val value2: Int){
    var mValue1: Int = value1;
    var mValue2: Int = value2;
    fun add(): Int{
        return mValue1 + mValue2
    }
}

class Child(value1: Int, value2: Int): Parent(value1, value2){
    fun sub(): Int{
        return mValue1 - mValue2
    }
}

/**
 *如果类内部的成员函数和通过扩展添加的成员函数冲突，则内部成员函数优先级更高
 *，因此，通过扩展无法覆盖内部成员函数
 */
class MyClass{
    private  var strValue = ""

    var mValue: Int = 0
    //内部属性
    var str: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor(){
    }

    private constructor(str: String){
        strValue = str
    }

    fun newInstance(value: Int): MyClass{
        return MyClass("内部成员函数")
    }

}

class D{
    fun bar(){
        Log.e(TAG, "D.bar")
    }
}
//在类中使用扩展
class C{
    fun baz(){
        Log.e(TAG, "C.baz")
    }

    //在类中对另外一个类扩展可以添加open关键字
    open fun D.foo(){
        bar()
        baz()
        Log.e(TAG, this@C.toString())//在D扩展函数中调用C的函数
    }

    fun caller(d: D){
        d.foo()
    }

}