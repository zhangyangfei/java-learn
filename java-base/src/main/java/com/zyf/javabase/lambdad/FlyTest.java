package com.zyf.javabase.lambdad;

public class FlyTest {
    public static void main(String[] args) {
        fly1(s -> System.out.println(s));
    }

    private static void fly1(Fly fly) {
        fly.fly("起飛了");
    }
}
