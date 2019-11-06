package cn.liuzehao.kotlin.kotlinOnePra.caseFive

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cn.liuzehao.kotlin.kotlinOnePra.caseFour.TAG

/**
 * Created by liuzehao on 2019-11-05.
 */
class FiveActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

fun data(){
    var user1 = User("liuzehao", 34)
    var user2 = User("liuzehao", 34)
    Log.e(TAG, "User ===> " + (user1 == user2))

    //数据类
    var people1 = People("liuzehao", 34)
    var people2 = People("liuzehao", 34)
    Log.e(TAG, "data class People ===> " + (people1 == people2))
}

class User(name: String, age: Int){
    var name = name
    var age = age

    override fun toString(): String {
        return "User(name = ${name}, age = ${age})"
    }

    override fun equals(other: Any?): Boolean {
        if(other is User){
            if(name == other.name && age == other.age){
                return true
            }
            return false
        }
        return false
    }
}

/**
 *数据类规定，属性要通过主构造器指定，而且数据类要在class关键字前面加data
 *该类与User的内部形式是一样的
 *注意：
 *    1.主构造器至少要有一个参数
 *    2.主构造器的所有参数必须标记为var或val
 *    3.数据类不能是抽象类、open类、封闭类或内部类
 *    4.要想数据类拥有一个没有参数的构造器，需要为主构造器每一个参数加上默认值，
 *    或添加一个没有参数的次级构造器，同时继承的主构造器的每一个参数需设置值
 */
data class People(val name: String, val age: Int)
data class Student(val name: String = "liuzehao", val age: Int = 26)
data class Teacher(val name: String, val age: Int){
    constructor(): this("liuzehao", 26){}
}