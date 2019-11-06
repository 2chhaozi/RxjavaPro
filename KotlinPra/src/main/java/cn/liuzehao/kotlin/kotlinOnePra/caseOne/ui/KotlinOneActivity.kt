package cn.liuzehao.kotlin.kotlinOnePra.caseOne.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import cn.liuzehao.kotlin.R
//import cn.liuzehao.kotlin.kotlinOnePra.domain.Forecast
import cn.liuzehao.kotlin.kotlinOnePra.caseOne.ForecastTest
/*import cn.liuzehao.kotlin.kotlinOnePra.domain.ForecastList
import cn.liuzehao.kotlin.kotlinOnePra.domain.RequestForecastCommand
import org.jetbrains.anko.async*/
import java.util.*
import kotlin.collections.HashMap

import kotlinx.android.synthetic.main.kotlin_one_layout.*

/**
 * Created by liuzehao on 2019-09-10.
 */
class KotlinOneActivity : AppCompatActivity() {
    private val TAG = "KotlinOneActivity"
    /*//定义一个初始值为Null的变量时需要在等号左侧加？
    private var forecastList: RecyclerView ?= null;*/
    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )

    //用 fun 关键字来定义函数方法
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_one_layout)
        initView()
        initData()
    }

    fun initView(){
        /**
         *定义类的一个变量并转型为RecyclerView
         *layoutManager会通过属性的方式被设置，而不是通过setter
         *对象实例化，去掉new关键字，这时构造函数仍会被调用
         *" ？"表示为null时不调用
         */
        forecastList?.layoutManager = LinearLayoutManager(this)

        /**
         *Anko库提供了非常简单的DSL来处理异步任务
         *uiThread依赖于调用者
         *第二个参数是一个函数，所以我们可以把它放在圆括号外面，大括号里面
         */
        /*async {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecastList?.adapter = ForecastListAdapter(result){forecast -> toast(forecast.date) }
            }
        }*/
    }

    fun initData(){
        toast("Hello World!")
        toast("Hello World!", Toast.LENGTH_LONG)

        /**
         *如果我们使用不可修改的对象，若我们需要修改这个对象的状态，
         *拷贝f1对象后只修改temperature的属性，没改变其他状态
         */
        val f1 = ForecastTest(Date(), 27.5f, "Shiny day")
        val f2 = f1.copy(temperature = 30f)

        /**
         *映射对象的每一个属性到一个变量中，这个过程就是多声明
         *用对象的属性同时对多个变量赋值
         */
        val (date, temperature, detals) = f1
        val map: HashMap<Int, String> = hashMapOf()
        for((key, value) in map){
            Log.e(TAG, "key: " + key + "//value " + value)
        }
    }


    /**
     *扩展函数是指在一个类上增加一种新的行为；Kotlin中扩展函数的一个优势是
     *我们不需要在调用方法的时候把整个对象当做参数传入。
     *下例的函数不需要传入任何context，可以被任何Context或者它的子类调用
     */
    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, message, duration).show()
    }

    class BaseType{
        /**
         *数字类型中不会自动转型。例如，你不能给Double变量分配一个Int
         *必须使用对应函数做一个明确的类型转换
         */
        val i: Int = 7
        val d: Double = i.toDouble()
        /**
         *字符不能直接作为一个数字来处理。需要对应函数转换
         */
        val c: Char = 'c'
        val e: Int = c.toInt()
        /**
         *位运算:| --> or;  & --> and
         */
        val FLAG1: Boolean = true
        val FLAG2: Boolean = false
        //JAVA
        /*int bitwiserOr = FLAG1 | FLAG2
        int bitwiseAnd = FLAG1 & FLAG2*/

        //Kotlin
        val bitwiserOr = FLAG1 or FLAG2
        val bitwiseAnd = FLAG1 and FLAG2

        /**
         *不明确具体类型，编译器自己识别
         */
        val f = 12 //Int
        val iHex = 0x0f //十六进制的Int
        val l = 3L //Long
        val j = 3.5 //Double
        val h = 3.5F //Float

        fun method1(){
            val s = "Example"
            val c = s[2] //这是字符'a'

            //迭代String
            for(c in s){
                print(c)
            }
        }
    }

    /**
     *属性
     */
    class Persion{
        var name: String = ""

        fun getInstance(){
            val persion = Persion()
            persion.name = "liuzehao"
            val otherName = persion.name
        }

    }
}