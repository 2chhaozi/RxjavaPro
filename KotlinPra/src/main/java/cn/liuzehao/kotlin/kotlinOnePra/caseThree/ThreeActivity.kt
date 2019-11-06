package cn.liuzehao.kotlin.kotlinOnePra.caseThree

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cn.liuzehao.kotlin.R

/**
 * Created by liuzehao on 2019-10-30.
 */
class ThreeActivity: AppCompatActivity() {




    companion object{
        const val TAG = "LiuZeHao"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun main(){
        var c = Customer()
        c.value2 = 30
        Log.e(TAG, "getter/setter ===>" + c.value2)

        //外部类调用内部类
        ThreeActivity().Inner().foo()
    }

    /**
     *类允许定义一个主构造器和若干个第二构造器。主构造器是类头的一部分，
     *紧跟在类型后面，构造器参数是可选的
     *如果主构造器没有任何注释（annotation）或修饰器，constructor关键字可以省略
     *
     *关键字也可以用于主构造器的参数，如果使用 var ，参数对于构造器来说是变量，
     *可以在构造器内部修改变量的值，如果构造器的参数使用 val 声明，参数就变成了常量，在构
     *造器内部不能修改该参数的值。要注意的是，即使使用 var 声明变量，在构造器内部修改参数
     *变量值后，并不会把修改的值传到对象外部。
     */
    class Persion constructor(var firstName: String){
        //主构造器参数对类属性进行初始化
        var name = firstName
        init {
            Log.e(TAG, "主构造器：" + firstName)
        }

        /**
         *第二构造器需要在类中声明，前面必须加constructor关键字
         *如果类中声明了主构造器，那么所有第二构造器都需要在声明后面调用主构造器，
         *或通过另外一个第二构造器间接调用主构造器
         *
         *在第二构造器中不能使用var和val,这意味着第二构造器参数是只读的，不能在构造器内部改变参数值
         */
        constructor(parent: Person): this("liuzehao"){
            Log.e(TAG, "第二构造器调用主构造器" )
        }
        constructor(): this(Person("liuzehao")){
            Log.e(TAG, "通过别的第二构造器间接调用主构造器" )
        }
    }

    /**
     *单例模式
     */
    class Singleton private constructor(){
        public var value: Singleton? = null
        private object mHolder{ val INSTANCE = Singleton()}

        companion object Factory{
            fun getInstance(): Singleton{
                return mHolder.INSTANCE
            }
        }
    }

    /**
     *getter和setter
     *如果属性是只读的，需要将属性声明为 val,
     *并只添加一个 setter 形式。如果属性是读写的，需要使用 var 声明属性，并添加 getter setter
     *形式。如果 getter setter 中只有一行实现代码，直接用等号（＝〉分隔 getter 和代码即可
     *如果包含多行代码，需要使用｛．．．｝处理
     */
    class Customer{
        //只读属性
        val name: String
            get() = "liuzehao"

        //读写属性
        var v: Int = 20
        var value1: Int
            get() = v
            set(value){
                println("value1属性被设置")
                v = value
            }

        //使用field标识符，将field当做成员变量使用，通过它读写属性值
        var value2: Int = 0
            get() = field
            set(value) {
                println("value2属性被设置")
                field = value
            }
    }

    /**
     *如果某个参数带默认值，那么该参数后面的所有参数都必须带默认值
     */
    class QACommunity(person: Person, value: Int = 1){
        fun LogQACommunity(url: String, schema: String = "https"){
            Log.e(TAG, "${schema}://${url}")
        }

        fun process(value:Int, name:String ="Bill", age:Int = 30, salary:Float = 4000F){
            Log.e(TAG, "value:${value}, name:${name}, age:${age}, salary:${salary }")
        }

        /**
         *可变参数用vararg关键字声明
         */
        fun addPersions(vararg persions: Person): List<Person>{
            //本地函数，定义在函数内部的函数，作用域在函数体内
            fun process(age: Int){
                Log.e(TAG, "本地函数：" + age);
            }
            process(26)

            val result = ArrayList<Person>()
            for(persion in persions){//in关键字遍历数组获取元素
                result.add(persion)
            }
            return result
        }

        fun test(){
            /**
             *如果只想修改最好一个参数的默认值，按照之前的方法需要先传入name和age参数值，才能指定salary值
             *此时可以用命名参数传递参数值，即在调用函数时指定函数的形参名
             */
            process(30, salary = 1500F)
            addPersions(Person("liuzehao"), Person("liuxiaosheng"))
        }
    }

    //使用inner关键字修饰内部类，能够让外部类的示例引用内部类
    inner class Inner{
        private val bar = 1
        fun foo() = 1
    }

    /**
     *Kotlin默认class不允许继承的，方法和属性也默认不可继承
     *显示使用open表示可继承的
     */
    open class Father{
        private val a =1//仅在类内部可以访问
        protected open val b = 2//子类也能访问
        internal val c = 3//任何在模块内部类都可以访问
        val d = 4 //默认public

        protected class Nested{
            public val e = 5
        }

        open fun getAge(): Int{
            return a;
        }
    }

    class child: Father(){
        //无法访问父类的a常量
        //可以访问b、c和d
        //Nested类与e变量可以访问
        override val b = 5

        /**
         *方法前加override表示方法可以在子类中被再次重写
         *不想被子类重写可以在override前加final
         */
        override fun getAge(): Int {
            return b
        }
    }

    /**
     *kotlin允许接口的方法包含默认的方法体。对于有方法体的接口方法，
     *并不要求一定重写方法
     */
    interface MyInterface {
        fun process()
        fun getName(): String{
            return "liuzehao"
        }
    }

    open class MyClass: MyInterface{

        override fun process() {
        }

        override fun getName(): String {
            return "liuxiaosheng"
        }

        open fun f(){}
    }

    /**
     *抽象方法不需要使用Open声明，本身就是可继承的
     */
    abstract class AbsClass: MyInterface{
        override abstract fun process()
    }
}
