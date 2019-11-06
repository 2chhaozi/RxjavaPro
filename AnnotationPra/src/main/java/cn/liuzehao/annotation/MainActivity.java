package cn.liuzehao.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //注解类型：注解包括元注解、java内置注解 & 自定义注解，一共有5种类型

    /*1.元注解
    *定义：一种标识/标签；是一种基本注解 = Android系统内置的注解
    *作用：标识/解释开发者自定义的注解
    * */

    /*元注解作用于注解 & 解释注解
    * 元注解在注解定义是进行定义
    * */
    //@元注解
    @interface Carson_Annotation{

    }
    //注解作用于Java代码 & 解释Java代码
    @Carson_Annotation
    class Carson{
    }

    //--------------------------------------------元注解类型

    /*@Retention定义：保留注解
    *作用：解释说明了注解的生命周期
    *下面该注解的作用：说明注解new_Annotation的生命周期 = 保留到程序运行时 & 被加载到JVM中
    *参数说明：
    *RetentionPolicy.RUNTIME:注解保留到程序运行时 & 会被加载到JVM中，所有在程序运行时可以获取到
    *RetentionPolicy.CLASS:注解只被保留到编译进行时 & 不会被加载到JVM中
    *RetentionPolicy.SOURCE:注解只在源码阶段保留 & 在编译进行时会被丢弃忽略
    * */
    @Retention(RetentionPolicy.RUNTIME)
    @interface new_Annotation{
    }


    /*@Documented定义：Java文档注解
    *作用：将注解中的元素包含到JavaDoc文档中
    *下面元注解作用：说明 注解other_Annotation中的元素的元素包含到JavaDoc文档中
    * */
    @Documented
    @interface other_Annotation{
    }


    /*@Target定义：目标注解
    *作用：限制了注解作用的目标范围，包括类、方法等
    *该元注解限制了 注解another_Annotation的目标范围 = 方法
    *即注解another_Annotation只能用来解释说明某个方法
    *参数说明：
    *ElementType.PACKAGE：可以给一个包进行注解
    *ElementType.ANNOTATION_TYPE：可以给一个注解进行注解
    *ElementType.TYPE：可以给一个类型进行注解，如类、接口、枚举
    *ElementType.CONSTRUCTOR：可以给构造方法进行注解
    *ElementType.METHOD：可以给方法进行注解
    *ElementType.PARAMETER 可以给一个方法内的参数进行注解
    *ElementType.FIELD：可以给属性进行注解
    *ElementType.LOCAL_VARIABLE：可以给局部变量进行注解
    * */
    @Target(ElementType.METHOD)
    @interface another_Annotation{
    }


    /*@Inherited定义：继承注解
    *作用：是一个被 @Inherited注解的注解 作用的类的子类也可以继承该类的注解
    *前提：子类没有被任何注解应用
    *
    * */
    @Inherited
    @interface one_Annotation{
    }

    @one_Annotation
    class A{
    }

    //B是A的子类，也就继承了A类的注解@one_Annotation
    class B extends A{
    }


    /*@Repeatable定义：可重复注解
    *作用：是的作用的注解可以取多个值
    * */

    /*定义容器注解@职业
    *定义：本身也是一个注解
    *作用：存放其它注解
    *具体使用：必须有一个 value 属性；类型 = 被 @Repeatable 注解的注解数组
    *如本例中，被 @Repeatable 作用 = @Person ，所以value属性 = Person []数组
    * */
    public @interface Persions{
        Persion[] value();
    }

    //@Repeatable()括号中的是存放注解内容的容器
    @Repeatable(Persions.class)
    public @interface Persion{
        String role() default "";
    }

    // 在使用@Person（被@Repeatable 注解 ）时，可以取多个值来解释Java代码
    // 下面注解表示：Man类即是学生，又是家庭主夫
    @Persion(role = "student")
   // @Persion(role = "husband")
    public class Man{
        String name = "";
    }


    //--------------------------------------------

    //Java内置注解

    /*@Deprecated定义：过时注解
    *作用：标记已过时 & 被抛弃的元素（类或方法等）
    * */
    //用@Deprecated标记类中已过时的方法playDnf(),使用该方法是IDE会提示该方法已过时/抛弃
    class Game{
        @Deprecated
        public void playDnf(){

        }
    }

    /*@Override定义：复写注解
    *作用：标记该方法需要被子类复写
    * */


    /*@SuppresWarnings定义：阻止警告注解
    *作用：标记的元素会阻止编译器发出警告提醒
    * */
    @SuppressWarnings("deprecation")
    public void test(){
        Game game = new Game();
        game.playDnf();
    }

    /*@SafeVarargs定义：参数安全类型注解
    *作用：提醒开发者不要用参数做不安全操作 & 阻止编译器产生unchecked警告
    * */
    // 虽然编译阶段不报错，但运行时会抛出 ClassCastException 异常
    // 所以该注解只是作提示作用，但是实际上还是要开发者自己处理问题
    @SafeVarargs // Not actually safe!
    static void m(List<String>... stringLists) {
        Object[] array = stringLists;
        List<Integer> tmpList = Arrays.asList(42);
        array[0] = tmpList; // Semantically invalid, but compiles without warnings
        String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
    }


    /*@FunctionalInterface定义：函数式接口注解
    *作用：表示该接口 =  函数式接口
    *函数式接口 = 1个具有1个方法的普通接口
    * */
    // 多线程开发中常用的 Runnable 就是一个典型的函数式接口（被 @FunctionalInterface 注解）
    @FunctionalInterface
    public interface Runnable {
        public abstract void run();
    }



    //---------------------------------------------
    //注解的定义、属性和具体使用


    /*注解的定义
    *通过@interface关键字进行定义
    *形式类似于接口，区别在于多一个@符号
    *下面的代码创建了一个名为New_point的注解
    * */
    @interface New_point{
    }

    /*注解的属性
    *在定义改注解本身时进行定义
    * */
    @interface New_Dream{
        //注解的属性 =  成员变量
        //注解只有成员变量，没有方法
        //注解@New_Dream中有2个属性：id和msg
        int id();
        String msg() default "Hi";

        /*说明：
        * 注解的属性以“无形参的方法”形式来声明
        * 方法名 = 属性名
        * 方法返回值 = 属性类型 = 8种基本类型 + 类、接口、注解以及对于的数组类型
        * 用default关键字指定属性的默认值，如上面的msg默认值 = “H”
        * */
    }

    /*注解的属性在使用时赋值
    * 注解的属性赋值方式 = 注解括号内以“value = “xx” ”形式；用“ ， ”隔开多个属性
    *
    * 注解New_Dream作用于D类
    * 在作用/使用时对注解属性进行赋值
    * */
    @New_Dream(id = 1,msg = "hello world!")
    class D{
    }

    /*注解的应用
    * 在类、成员变量、方法定义前加上“@注解名”就可以使用该注解
    * */
    @New_point
    class F{
        @New_point
        int a;

        @New_point
        void bMethod(){
            D.class.isAnnotationPresent(New_point.class);
        }
    }



    /*获取注解
    *定义：通过反射技术获取某个对象上的所有注解
    *<-- 步骤1：判断该类是否应用了某个注解 -->
    *手段：采用 Class.isAnnotationPresent()
    *public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {}

    *<-- 步骤2：获取 注解对象（Annotation）-->
    *手段1：采用getAnnotation()  ；返回指定类型的注解
    *public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}
    *手段2：采用getAnnotations() ；返回该元素上的所有注解
    *public Annotation[] getAnnotations() {}
    * */


    // 因为注解@Carson_Annotation需要在程序运行时使用
    // 所以必须采用元注解Retention(RetentionPolicy.RUNTIME)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Carson_Annotation2{
        int id();
        String msg() default "Hi";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Carson_Annotation3{
        int id();
        String msg() default "Hi";
    }

    // 1个注解作用于Test类
    @Carson_Annotation2(id = 1,msg="我是类Test")
    public class Test2 {

        // 1个注解 作用于Test类成员变量a
        @Carson_Annotation2(id = 2,msg="我是变量a")
        int a;

        // 2个注解 作用于Test类方法
        @Carson_Annotation2(id = 3,msg="我是方法b")
        @Carson_Annotation3(id = 4,msg="我是方法bb（来自第2个注解）")
        public void bMethod(){}
    }

    public void getMyAnnotation(){
        String TAG = "getMyAnnotation";
        /**
         * 讲解1：获取类上的注解
         */

        // 1. 判断Test类是否应用了注解@Carson_Annotation
        boolean hasAnnotation = Test2.class.isAnnotationPresent(Carson_Annotation2.class);

        // 2. 如果应用了注解 = hasAnnotation = true
        //    则获取类上的注解对象
        if ( hasAnnotation ) {
            Carson_Annotation2 classAnnotation = Test2.class.getAnnotation(Carson_Annotation2.class);

            // 3. 获取注解对象的值
            Log.d(TAG, "我是Test类上的注解");
            Log.d(TAG, "id:" + classAnnotation.id());
            Log.d(TAG, "msg:" + classAnnotation.msg());
        }

        /**
         * 讲解2：获取类成员变量a上的注解
         */
        try {
            // 1. 获取类上的成员变量a
            Field a = Test2.class.getDeclaredField("a");
            a.setAccessible(true);

            // 2. 获取成员变量a上的注解@Carson_Annotation
            Carson_Annotation2 variableAnnotation = a.getAnnotation(Carson_Annotation2.class);

            // 3. 若成员变量应用了注解 = hasAnnotation = true
            //    则获取注解对象上的值 = id & msg
            if ( variableAnnotation != null ) {
                Log.d(TAG, "我是类成员变量a上的注解");
                Log.d(TAG, "id:" + variableAnnotation.id());
                Log.d(TAG, "msg:" + variableAnnotation.msg());
            }

            /**
             * 讲解3：获取类方法bMethod上的注解
             */
            // 1. 获取类方法bMethod
            Method testMethod = Test2.class.getDeclaredMethod("bMethod");

            // 2. 获取方法上的注解
            if ( testMethod != null ) {
                // 因为方法上有2个注解，所以采用getAnnotations()获得所有类型的注解
                Annotation[] ans = testMethod.getAnnotations();
                Log.d(TAG, "我是类方法bMethod的注解");
                // 3. 获取注解的值（通过循环）
                for( int i = 0;i < ans.length  ;i++) {
                    Log.d(TAG, "类方法bMethod的" + "注解"+ i+ ans[i].annotationType().getSimpleName());
                }
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


}
