package cn.liuzehao.kotlin.kotlinOnePra.caseOne

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

/**
 * Created by liuzehao on 2019-09-10.
 */
class KotlinOneTest : AppCompatActivity(){
    val TAG: String = "KotlinOneTest"

    //用 fun 关键字来定义函数方法
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /**
     *如果没有指定返回值，就会返回Unit,与Java中的void类似，
     *但是Unit是一个真正的对象。下面是指定了Int类型的返回值
     */
    fun add(x: Int, y: Int) : Int{
        return x + y
    }

    /**
    如果返回的结果可以是一个表达式计算出来，可以使用等号来代替括号
     */
    fun reduce(x: Int, y: Int) : Int = x - y

    /**
     *给参数指定一个默认值使得他们变得可选,这意味着你调用
     *的时候可以传入第二个值或者不传，这样可以避免重载函数
     */
    fun toast(message: String, length: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this, message, length).show()
    }

    fun condition(x: String, view: View){
        val x1 = if(x.equals("2")) "1" else "1"
        when(x1){
            "1" -> Log.e(TAG, "condition-->1")
            "2" -> Log.e(TAG, "condition-->1")
            else -> {
                Log.e(TAG, "condition-->else")
            }
        }

        val x2 = when(x1){
            "0", "1" -> "liuzehao"
            else -> "liuxiaosheng"
        }

        when(view){
            is TextView -> view.setText("I am a TextView")
            is EditText -> toast("EditText value: ${view.text}")
            is ViewGroup -> toast("ViewGroup child: ${view.childCount}")
            else -> {
                view.visibility = View.GONE
            }
        }

        /**
         *再条件右边的代码中，参数会被自动转型，所以你不需要去明确地做类型转换。
         *它还让检测参数否在一个数组范围甚至是集合范围成为可能
         */
        val x3 = x2.toInt()
        when(x3){
            in 1..10 -> "hehe"
            in 10..20 -> "haha"
        }

        val array = arrayOf<Int>(1, 2, 3)
        for(i in array.indices){
            Log.e(TAG, "condition-->array:" + array[i])
        }

        val TAG: String = ""
        //从低到高不会自动增长，但可以使用downTo函数
        for(i in 10..0){
            Log.e(TAG, "liuzehao")
        }
        for(i in 10 downTo 0){
            Log.e(TAG, "liuzehao" + i)
        }
        //创建一个开区间（不包含最后项）
        for(i in 0 until 10){
            Log.e(TAG, "liuzehao" + i)
        }
        //从一个ViewGroup中得到一个views列表
        val vewGroup = LinearLayout(applicationContext);
        val views = (0..vewGroup.childCount - 1)
                .map { vewGroup.getChildAt(it) }
    }

    fun operator(){
        val list = listOf(1, 2, 3, 4, 5, 6)
        //至少有一个元素符号给出的条件，则返回true
        list.any{it % 2 == 0}
        //全部的元素符合给出的条件，则返回true
        list.all {  it < 10  }
        //返回符合条件的元素总数
        list.count { it % 2 == 0 }
        //在一个初始值的基础上从第一项到最后一项通过一个函数累计所有的元素
        list.fold(8){ total, next -> total + next}
        //与fold一样，但是顺序是从最后一项到第一项
        list.foldRight(8){ total, next -> total + next}
        //遍历所有元素，并执行给定的操作
        list.forEach{ println(it) }
        //与forEach一样，但是同时可以得到元素的index
        list.forEachIndexed { index, value -> println("position $index contains a $value") }
        //返回最大的一项，如果没有则返回Null
        list.max()
        //根据给定的函数返回最大的一项，如果没有则返回null
        list.maxBy { -it }
        //返回最小的一项，如果没有则返回Null
        list.min()
        //根据给定的函数返回最小的一项，如果没有则返回null
        list.minBy { -it }
        //如果没有任何元素与给定函数匹配，则返回true
        list.none { it % 7 == 0 }
        //与fold一样，但是没有一个初始值。通过一个函数从第一项到最后一项进行累计
        list.reduce { total, next -> total + next }
        //reduceRight:与reduce一样，不过是顺序是倒数开始累计的

        //返回所有每一项通过函数转换之后的数据的总和
        list.sumBy { it % 2 }
        //包含去掉前n个元素的所有元素的列表
        list.drop(3)
        //返回根据给定函数从第一项开始去掉指定元素的列表
        list.dropWhile { it < 3 }
        //返回根据给定函数从最后一项开始去掉指定元素的列表
        list.dropLastWhile { it > 4 }
        //过滤所有不符合给定函数条件的元素
        list.filterNot { it % 2 == 0 }
        //过滤所有元素中不是null的元素
        list.filterNotNull()
        //过滤一个list中指定index的元素
        list.slice(listOf(1, 3, 4))
        //返回从第一个开始的n个元素
        list.take(2)
        //返回从最后一个开始的n个元素
        list.takeLast(2)
        //返回从第一个开始符合给定函数条件的元素
        list.takeWhile { it < 3 }
        //遍历所有的元素，为每一个创建一个集合，最后把所有的集合放在一个集合中
        // == listOf(1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7)
        list.flatMap { listOf(it, it + 1) }
        //返回一个根据给定函数分组后的map
        list.groupBy { if (it % 2 == 0) "even" else "odd" }
        //返回一个每一个元素根据给定的函数转换所组成的List
        list.map { it * 2 }
        //返回一个每一个元素根据给定的包含元素index的函数转换所组成的List
        list.mapIndexed { index, it -> index * it }
        //返回一个每一个非null元素根据给定的函数转换所组成的List
        list.mapNotNull { it * 2 }
        //如果指定元素可以在集合中找到，则返回true
        list.contains(2)
        //返回给定index对应的元素，如果index数组越界则会抛出 IndexOutOfBoundsException
        list.elementAt(1)
        //返回给定index对应的元素，如果index数组越界则会根据给定函数返回默认值
        list.elementAtOrElse(10, { 2 * it })
        //返回给定index对应的元素，如果index数组越界则会返回null
        list.elementAtOrNull(10)
        //返回符合给定函数条件的第一个元素
        list.first { it % 2 == 0 }
        //返回符合给定函数条件的第一个元素，如果没有符合则返回null
        list.firstOrNull { it % 7 == 0 }
        //返回符合给定函数条件的最后一个元素
        list.last { it % 2 == 0 }
        //返回指定元素的第一个index，如果不存在，则返回 -1
        list.indexOf(4)
        //返回第一个符合给定函数条件的元素的index，如果没有符合则返回 -1
        list.indexOfFirst { it % 2 == 0 }
        //返回最后一个符合给定函数条件的元素的index，如果没有符合则返回 -1
        list.indexOfLast { it % 2 == 0 }
        //返回符合给定函数的单个元素，如果没有符合或者超过一个，则抛出异常
        list.single { it % 5 == 0 }
        //返回符合给定函数的单个元素，如果没有符合或者超过一个，则返回null
        list.singleOrNull { it % 7 == 0 }
        /**
         *把两个集合合并成一个新的，相同index的元素通过给定的函数进行合并成新的元素
         *作为新的集合的一个元素，返回这个新的集合。新的集合的大小由最小的那个集合
         *大小决定
         */
        val listRepeated = listOf(2, 2, 3, 4, 5, 5, 6)
        //list.merge(listRepeated) { it1, it2 -> it1 + it2 }

        /**
         *把一个给定的集合分割成两个，第一个集合是由原集合每一项元素匹配给定函数条
         *件返回 true 的元素组成，第二个集合是由原集合每一项元素匹配给定函数条件返
         *回 false 的元素组成
         */
        list.partition { it % 2 == 0 }
        /**
         *返回一个包含原集合和给定集合中所有元素的集合，因为函数的名字原因，我们可
         *以使用 + 操作符
         */
        list + listOf(7, 8)
        /**
         *返回由 pair 组成的List，每个 pair 由两个集合中相同index的元素组成。这个返
         *回的List的大小由最小的那个集合决定
         *listOf(Pair(1, 7), Pair(2, 8))
         */
        list.zip(listOf(7, 8))
       /**
        *从包含pair的List中生成包含List的Pair
        *Pair(listOf(5, 6), listOf(7, 8))
        */
        listOf(Pair(5, 7), Pair(6, 8)).unzip()
        //返回一个与指定list相反顺序的list
        list.reversed()
        //返回一个自然排序后的list
        list.sorted()
        //返回一个根据指定函数排序后的list
        list.sortedBy { it % 3 }
        //返回一个降序排序后的list
        list.sortedDescending()
        //返回一个根据指定函数降序排序后的list
        list.sortedByDescending { it % 3 }

    }

    /**
     *所有的类默认都是不可继承的（final），我们只能继承那些明确声明open或者abstract的类
     *当我们只有单个构造器时，我们需要在从父类继承下来的构造器中指定需要的参
     *数。这是用来替换Java中的 super 调用的。
     */
    open class Animal(name: String)

    class Persion(name: String, surname: String) : Animal(name){
        init {

        }
    }

}