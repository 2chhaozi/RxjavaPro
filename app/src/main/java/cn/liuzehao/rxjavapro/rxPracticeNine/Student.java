package cn.liuzehao.rxjavapro.rxPracticeNine;

import java.util.List;

/**
 * Created by liuzehao on 2019/9/5.
 */
public class Student {
    private String name;
    private int age;
    private List<String> course;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }
}
