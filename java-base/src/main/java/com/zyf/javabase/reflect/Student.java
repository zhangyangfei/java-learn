package com.zyf.javabase.reflect;

public class Student {

    private String name;
    private int age;

    // 子类不继承父类的构造方法

    public Student() {

    }

    private Student(String name) {
        this.name = name;
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

    public void method1() {
        System.out.println("method1：我是方法1");
    }

    private void method2(String str) {
        System.out.println("method2：" + str);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + this.getName() + '\'' +
                ", age=" + this.getAge() +
                '}';
    }
}
