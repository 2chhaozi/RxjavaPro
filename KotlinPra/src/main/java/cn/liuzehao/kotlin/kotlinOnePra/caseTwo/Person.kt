import a.b.*
/**
 *导入某个对应定义包名下的所有内容
 *import提供了给导入资源起一个别名的功能，同时原资源名不可用，如：
 *import a.b.getName as f
 */
fun main(args: Array<String>){
    println(getName())
    println(MyClass())
}