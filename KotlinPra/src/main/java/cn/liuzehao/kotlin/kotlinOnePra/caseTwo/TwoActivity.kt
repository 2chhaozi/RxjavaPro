package cn.liuzehao.kotlin.kotlinOnePra.caseTwo

import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.lang.IllegalArgumentException

/**
 * Created by liuzehao on 2019-10-22.
 */
class TwoActivity: AppCompatActivity() {
    val TAG = "LiuXiaoSheng"

    /**
     *定义Kotlin函数时，函数头除了包含函数名外，还必须包含fun关键字。
     *如果函数没有返回值，可以返回Unit，也可以什么都不返回（省略Unit）
     */
    fun add(m: Int, n: Int): Int{
        return m + n
    }

    fun process(m: Int): Unit{
        println(m * m)
    }

    /**
     *表示Long或Float类型的值，在数值后面加：123L、123.1F
     *十六进制：0x1F;
     *二进制：0b100101
     *
     *在Kotlin中Char不能直接看做数字
     *
     *Kotlin无法隐式转换，除了Int转换为Long
     *方法：
     *      toByte()
     *      toShort()
     *      toInt()
     *      toLong()
     *      toFloat()
     *      toDouble()
     *      toChar()
     */
    fun decimalDigitValue(c: Char): Int{
        if(c !in '0'..'9'){
            throw IllegalArgumentException("out of range")
        }
        return c.toInt() - '0'.toInt()
    }

    /**
     *数组
     */
    fun defineArrray(){
        //使用arrayOf函数定义可以存储任意值的数组
        val arr1 = arrayOf(1, 2, 3, 4, 'a')
        arr1[2] = 'b'

        var arr2 = arrayOfNulls<Int>(10)

        //使用 Array 类的构造器定义数组，其中第二个参数是指初始化每一个数组元素的值
        //每个数组元素的值就是当前数组索引的乘积
        val arr3 = Array(10, { i -> (i * i).toString()})

        //使用inArrayOf函数定义数组
        var arr4: IntArray = intArrayOf(20, 30 ,40 , 50)

    }

    /**
     *字符串
     */
    fun defineString(){
        val s1 = "hello\nworld"

        /**
         *保留原始格式的字符串，这种字符串不能用转义字符，如果字符串中带有格式，如换行，
         *直接写在字符串中就行
         */
        val s2 = """
                hello
                world 
        """

        //字符串模板。用$,若是表达式用：${}
        val i = 10
        val s3 = "i = $i" //相当于 s1 = "i = 10"

        val s4 = "abc"
        val s5 = "$s4 的长度是 ${s4.length}"
    }

    /**
     *条件语句
     */
    fun defineCondition(){
        var a = 10
        var b = 20
        val max = if(a > b) a else b

        //后面是代码块
        val min = if(a > b){
            Log.e(TAG, "Choose a")
            a //返回值
        }else{
            Log.e(TAG, "Choose b")
            b //返回值
        }

        //-----------------------

        /**
         *when 语句会根据传入的值（这里是 ）寻找第 个满足条件的分支，找到后执行分支的语句
         *如果分支中多于 条语旬，要用｛．．．｝
         *满足条件的分支执行后，会自动终止 hen 语句的执行，因此，并不需要像 switch
         *句那样每 case 语句都加上 break
         */
        var x = 1
        when(x){
            1 -> {
                Log.e(TAG, "x == 1")
                Log.e(TAG, "Hello World!")
            }
            2 -> Log.e(TAG, "x == 2")
            else -> {
                Log.e(TAG, "”x is ne ther 1 nor 2")
            }
        }

        /**
         *when作为表达式时，第一个满足条件的分支的最后一个表达式就是when表达式的返回值
         *若多个分支条件执行的代码一样，可以在一个分支用“ ， ”分割多个条件
         *如果要执行相同代码的条件比较多，或无法枚举，可使用 in 和 !in 关键字确定一个范围
         */
        var y = 1
        var m = when(y){
            1 -> {
                Log.e(TAG, "y == 1")
                20
            }
            2,3 -> {
                Log.e(TAG, "y == 2,3")
                30
            }
            in 4..10 -> {
                Log.e(TAG, "y == 4-10")
                40
            }
            !in 11..20 -> {
                Log.e(TAG, "y != 11-20")
                50
            }
            else -> {
                Log.e(TAG, "y == else")
                60
            }
        }

        //when的分支条件不仅可以是常量，也可以是任意表达式，如函数
        var n = 9
        when(n){
            getValue(2) -> Log.e(TAG, "不满足条件")
            getValue(3) -> Log.e(TAG, "满足条件")
            else -> Log.e(TAG, "条件未知")
        }

        //--------------------------------------------------------

        var arr = intArrayOf(2, 4, 6, 8, 10)
        for(i in arr){
            Log.e(TAG, "arr[$i] = " + arr[i])
        }
        //在循环时，同时对索引和元素值进行循环
        for((index, value) in arr.withIndex()){
            Log.e(TAG, "arr[$index] = " + value)
        }
    }

    fun getValue(x: Int): Int{
        return x * x
    }


}