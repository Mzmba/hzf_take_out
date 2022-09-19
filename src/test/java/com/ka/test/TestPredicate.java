package com.ka.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @date 2019/10/29 23:15
 * @auther wangbo
 */
//
//@FunctionalInterface
//public interface Predicate<T> {
//    boolean test(T t);
//}
public class TestPredicate {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("123", "1234", "12345");
        //传递 predicate 条件
        List<String> result = filter(list, new Predicate<String>() {
            @Override
            public boolean test(String x) {
                System.out.println("我是人");
                return x.length() > 4;
            }
        });
        System.out.println(result);
    }

    public static <T>List<T> filter(List<T> list, Predicate<T> predicate){
        List<T> result = new ArrayList<>();
        //遍历list参数列表，把符合 predicate 条件的元素存储到result中
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
