package cn.liuzehao.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 *1.用注解(Annotation)来标注目标类中所依赖的其他类，同样用注解来标注所依赖的其他类的构造函数，
 * 那注解的名字就叫Inject;这样我们就可以让目标类中所依赖的其他类与其他类的构造函数之间有了一种无形的联系。
 * 但是要想使它们之间产生直接的关系，还得需要一个桥梁来把它们之间连接起来。那这个桥梁就是Component。
 *2.Component也是一个注解类，一个类要想是Component，必须用Component注解来标注该类，并且该类是接口或抽象类。
 * Component需要引用到目标类的实例，Component会查找目标类中用Inject注解标注的属性，查找到相应的属性后会接着查找该属性对应的用Inject标注的构造函数（这时候就发生联系了），
 * 剩下的工作就是初始化该属性的实例并把实例进行赋值。因此我们也可以给Component叫另外一个名字注入器（Injector）。
 *
 * 小结:
 * 目标类想要初始化自己依赖的其他类：
 * 用Inject注解标注目标类中其他类
 * 用Inject注解标注其他类的构造函数
 * 若其他类还依赖于其他的类，则重复进行上面2个步骤
 * 调用Component（注入器）的injectXXX（Object）方法开始注入（injectXXX方法名字是官方推荐的名字,以inject开始）。
 *
 * 3.项目中使用到了第三方的类库，第三方类库又不能修改，所以根本不可能把Inject注解加入这些类中，这时我们的Inject就失效了。
 * 那我们可以封装第三方的类库，封装的代码怎么管理呢,可以把封装第三方类库的代码放入Module中，例如：
 * @Module
 *     public class ModuleClass{
 *           //A是第三方类库中的一个类
 *           A provideA(){
 *                return A();
 *           }
 *     }
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 * Component是注入器，它一端连接目标类，另一端连接目标类依赖实例，它把目标类依赖实例注入到目标类中。
 * 上文中的Module是一个提供类实例的类，所以Module应该是属于Component的实例端的（连接各种目标类依赖实例的端），
 * Component的新职责就是管理好Module，Component中的modules属性可以把Module加入Component，modules可以加入多个Module
 *
 *4.Module中的创建类实例方法用Provides进行标注，Component在搜索到目标类中用Inject注解标注的属性后，
 * Component就会去Module中去查找用Provides标注的对应的创建类实例方法，这样就可以解决第三方类库用dagger2实现依赖注入了。
 *
 *5.创建类实例有2个维度可以创建：
 *
 * 通过用Inject注解标注的构造函数来创建（以下简称Inject维度）
 * 通过工厂模式的Module来创建（以下简称Module维度）
 * 这2个维度是有优先级之分的，Component会首先从Module维度中查找类实例，若找到就用Module维度创建类实例，并停止查找Inject维度。
 * 否则才是从Inject维度查找类实例。所以创建类实例级别Module维度要高于Inject维度。
 * 现在有个问题，基于同一个维度条件下，若一个类的实例有多种方法（多个构造方法）可以创建出来，那注入器（Component）应该选择哪种方法来创建该类的实例呢？
 * 把上面遇到的问题起个名字叫依赖注入迷失。
 * 那么可以给不同的创建类实例的方法用标识进行标注，用标识就可以对不同的创建类实例的方法进行区分（标识就如给不同的创建类实例方法起了一个id值）。
 * 同时用要使用的创建类实例方法的标识对目标类相应的实例属性进行标注。那这样我们的问题就解决了，提到的标识就是Qualifier注解，当然这种注解得需要我们自定义。
 * Qualifier（限定符）就是解决依赖注入迷失问题的。
 * 注意：
 * dagger2在发现依赖注入迷失时在编译代码时会报错。
 *
 *6.Scope的作用：
 * Dagger2可以通过自定义Scope注解，来限定通过Module和Inject方式创建的类的实例的生命周期能够与目标类的生命周期相同。
 * 或者可以这样理解：通过自定义Scope注解可以更好的管理创建的类实例的生命周期。
 *
 * 具体作用：
 * (1)更好的管理Component之间的组织方式，不管是依赖方式还是包含方式，都有必要用自定义的Scope注解标注这些Component，这些注解最好不要一样了，不一样是为了能更好的体现出Component之间的组织方式。
 *    还有编译器检查有依赖关系或包含关系的Component，若发现有Component没有用自定义Scope注解标注，则会报错。
 * (2)更好的管理Component与Module之间的匹配关系，编译器会检查 Component管理的Modules，若发现标注Component的自定义Scope注解与Modules中的标注创建类实例方法的注解不一样，就会报错。
 * (3)可读性提高，如用Singleton标注全局类，这样让程序猿立马就能明白这类是全局单例类。
 *
 *7.Component组织方式:
 * 要有一个全局的Component(可以叫ApplicationComponent),负责管理整个app的全局类实例（全局类实例整个app都要用到的类的实例，这些类基本都是单例的，后面会用此词代替）
 * 每个页面对应一个Component，比如一个Activity页面定义一个Component，一个Fragment定义一个Component。
 * 当然这不是必须的，有些页面之间的依赖的类是一样的，可以公用一个Component。
 *
 * 具体的组织方式:
 * 依赖方式
 * 一个Component是依赖于一个或多个Component，Component中的dependencies属性就是依赖方式的具体实现
 *
 * 包含方式
 * 一个Component是包含一个或多个Component的，被包含的Component还可以继续包含其他的Component。这种方式特别像Activity与Fragment的关系。SubComponent就是包含方式的具体实现。
 *
 * 继承方式
 * 官网没有提到该方式，具体没有提到的原因我觉得应该是，该方式不是解决类实例共享的问题，而是从更好的管理、维护Component的角度，把一些Component共有的方法抽象到一个父类中，然后子Component继承。
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/**
 * 1.Provide 如果是单例模式 对应的Compnent 也要是单例模式
 * 2.inject(Activity act) 不能放父类
 * 3.即使使用了单利模式，在不同的Activity 对象还是不一样的
 * 4.依赖component， component之间的Scoped 不能相同
 * 5.子类component 依赖父类的component ，子类component的Scoped 要小于父类的Scoped，Singleton的级别是Application
 * 6.多个Moudle 之间不能提供相同的对象实例
 * 7.Moudle 中使用了自定义的Scoped 那么对应的Component 使用同样的Scoped
 */
