package com.zyf.javabase.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射
 */
public class reflectTest {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        /*
            获取类的Class对象的三种方法：
                类名.class
                Object的getClass()
                Class.forName("类的全限定名")
         */

        // 类的全限定名字符串
        String classStr = "com.zyf.javabase.reflect.Student";
//        String classStr = "com.zyf.javabase.reflect.Teacher";
//        String classStr = "com.zyf.javabase.reflect.Person";
        // 获取类Class对象
        Class<?> clazz = Class.forName(classStr);


        constructorTest(clazz);
        fieldTest(clazz);
        methodTest(clazz);
    }

    /**
     * 反射获取method
     */
    private static void methodTest(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<?> con = clazz.getDeclaredConstructor();
        con.setAccessible(true);
        Object obj = con.newInstance();

        // 本类及继承的方法
        Method[] methods = clazz.getMethods();

        // public方法，包括继承的
        Method method1 = clazz.getMethod("method1");
        method1.invoke(obj);

        // 返回单个成员方法对象
        Method method2 = clazz.getDeclaredMethod("method2", String.class);
        method2.setAccessible(true);
        method2.invoke(obj, "你好");
    }

    /**
     * 反射获取Field
     */
    private static void fieldTest(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchFieldException {
        Constructor<?> con = clazz.getDeclaredConstructor();
        con.setAccessible(true);
        Object obj = con.newInstance();

        // 获取本来的field。（父类的Field无法获取，会报错 java.lang.NoSuchFieldException）
        Field nameField = clazz.getDeclaredField("name");
        // 对于私有Field，暴力反射
        nameField.setAccessible(true);
        // 给实例的name字段赋值
        nameField.set(obj, "李四");
        System.out.println(obj);
    }

    /**
     * 反射获取构造函数
     */
    private static void constructorTest(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        // 获取类的无参构造方法
        Constructor<?> con = clazz.getConstructor();//获取public构造方法

        // 获取类的实例
        Object obj = con.newInstance();
        System.out.println(obj);

        // 获取类的有参构造方法
        // Constructor<?> con2 = clazz.getConstructor(String.class);// 报错：java.lang.NoSuchMethodException
        // 获取所有声明的构造方法,private
        Constructor<?> con2 = clazz.getDeclaredConstructor(String.class);
        // 对于私有构造方法，暴力反射
        con2.setAccessible(true);
//        clazz.newInstance();// 类对象只能获取空参构造方法
        Object obj2 = con2.newInstance("张三");
        System.out.println(obj2);

        // 多参数构造方法
        Constructor<?> con3 = clazz.getConstructor(String.class, int.class);
        Object obj3 = con3.newInstance("张三", 30);
        System.out.println(obj3);

    }
}
