package com.zyf.javabase.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
//        test4();
//        test5();
    }

    private static void test5() {
        Stream<String> s1 = Stream.of("张三", "李四");
        Stream<String> s2 = Stream.of("张三", "王五");
        Stream<String> concat = Stream.concat(s1, s2);
        concat.forEach(System.out::println);
        System.out.println("---------------------------------------");
        Stream.concat(Stream.of("张三", "李四"), Stream.of("张三", "王五")).distinct().forEach(System.out::println);
    }

    private static void test4() {
        Integer reduce1 = Stream.of(1, 2, 3, 4, 5, 6, 5).reduce(0, (x, y) -> x + y);
        System.out.println(reduce1);
        Integer reduce2 = Stream.of(1, 2, 3, 4, 5, 6, 5).reduce(0, Integer::sum);
        System.out.println(reduce2);
        Integer reduce3 = Stream.of(1, 2, 3, 4, 5, 6, 5).reduce(0, (x, y) -> x <= y ? y : x);
        System.out.println(reduce3);
    }

    private static void test3() {
        List<String> list = Arrays.asList("zhangsan1,90", "lisi,80", "wangwu,70", "zhangsan2,99", "zhangsan3,50", "zhangsan4,55", "zhangsan5,76");
        Map<String, List<String>> collect = list.stream().collect(Collectors.groupingBy(s -> {
            int score = Integer.parseInt(s.split(",")[1]);
            if (score >= 90) {
                return "优秀";
            } else if (score >= 80) {
                return "良好";
            } else if (score >= 70) {
                return "一般";
            } else if (score >= 60) {
                return "及格";
            } else {
                return "不及格";
            }
        }));
        System.out.println(collect);
        System.out.println("---------------------------------------");
        Optional<String> collect1 = list.stream().collect(Collectors.maxBy((s1, s2) -> {
            int score1 = Integer.parseInt(s1.split(",")[1]);
            int score2 = Integer.parseInt(s2.split(",")[1]);
            return score1 - score2;
        }));
        System.out.println(collect1.get());
        System.out.println("---------------------------------------");
        Optional<String> collect2 = list.stream().collect(Collectors.minBy((s1, s2) -> {
            int score1 = Integer.parseInt(s1.split(",")[1]);
            int score2 = Integer.parseInt(s2.split(",")[1]);
            return score1 - score2;
        }));
        System.out.println(collect2.get());
    }

    private static void test2() {
        List<String> list = Arrays.asList("1.1", "2.2", "3.3", "4.4", "5.5");

        List<Double> collect = list.stream().map(s -> Double.valueOf(s)).collect(Collectors.toList());
        System.out.println(collect);
        double sum1 = collect.stream().mapToDouble(s -> s).sum();

        double sum = list.stream().mapToDouble(s -> Double.valueOf(s)).sum();
        System.out.println(sum);

        OptionalDouble average = list.stream().mapToDouble(s -> Double.valueOf(s)).average();
        average.ifPresent(System.out::print);
    }

    private static void test1() {
        List<String> list = Arrays.asList("zhangsan1", "lisi", "wangwu", "zhaoliu", "zhangsan2", "zhangsan3", "zhangsan4");
        List<String> zhang = list.stream()
                .filter(s -> s.startsWith("zhang"))
                .skip(2)
                .limit(2)
                .sorted((s1, s2) -> s2.compareToIgnoreCase(s1))
                .collect(Collectors.toList());
        System.out.println(zhang);
    }
}
