package com.zyf.javabase.lambdad;

public class AddTest {
    public static void main(String[] args) {

        add1((int x, int y) -> {
            return x + y;
        });

        // 参数类型可以省略
        add1((x, y) -> {
            return x + y;
        });

        // 执行代码块只有一行代码，可以省略大括号、return、分号
        add1((x, y) -> x + y);

        // 右侧表示实现Add接口的实现类，但是只将重写方法的内部逻辑代码展示，一切从简
        Add a = (x, y) -> x + y;

        Runnable ra = () -> System.out.println("重写方法run方法");
    }

    private static void add1(Add add) {
        int sum = add.add(1, 2);
        System.out.println(sum);
    }


}
