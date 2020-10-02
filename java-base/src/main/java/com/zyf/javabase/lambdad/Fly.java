package com.zyf.javabase.lambdad;

public interface Fly {
    void fly(String str);

    default void df(){
        System.out.println("默认方法");
    }
}
