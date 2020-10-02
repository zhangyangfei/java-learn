package com.zyf.javabase.refrence;

/**
 * lambda方法引用测试
 *      lambda内的方法逻辑，用其他类的方法来代替
 */
public class RefrenceTest {
    public static void main(String[] args) {
        /**
         * 引用类方法
         *      语法：类名::静态方法
         *      以Add.addMethod1()作为实现AddInterf.add()的方法体
         */
        useRef(Add::addMethod1);

        /**
         * 引用对象的实例方法
         *      语法：对象::成员方法
         *      以Add实例.addMethod1()作为实现AddInterf.add()的方法体
         */

        useRef(new Add()::addMethod2);

        /**
         * 引用类的实例方法
         *      语法：类名::成员方法
         *      以String类的substring方法作为实现StringInterf.subStr()的方法体
         *      StringInterf.subStr()需要三个参数，String.substring()需要两个参数，前者的第一个参数作为实例，即【第一个参数.substring(第二参数、第三参数)】
         */
        useSubStr(String::substring);

        /**
         * 引用构造方法
         *      语法：类名::new
         *      new是指以Student的参数相同构造函数作为StudentBuilder.build的方法体
         */

        useStudentBuild(Student::new);
    }

    private static void useRef(AddInterf ai) {
        int sum = ai.add(1, 2);
        System.out.println(sum);
    }

    private static void useSubStr(StringInterf si) {
        String str = si.subStr("hellorefrence", 2, 6);
        System.out.println(str);

    }

    private static void useStudentBuild(StudentBuilder sb) {
        Student stu = sb.build("张三", 10);
        System.out.println(stu.getName() + "，" + stu.getAge());
    }
}
