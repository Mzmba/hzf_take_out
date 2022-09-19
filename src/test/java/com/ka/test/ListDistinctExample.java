package com.ka.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListDistinctExample {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(3);
            add(5);
            add(2);
            add(1);
            add(3);
            add(7);
            add(2);
            add(2);
            add(2);
        }};
        System.out.println("原集合:" + list);
        method_1(list);
    }

    /**
     * 使用迭代器去重
     * @param list
     */
    public static void method_1(List<Integer> list) {
        Iterator<Integer> willbelist = list.iterator();
        while (willbelist.hasNext()) {
            // 获取循环的值
            Integer item = willbelist.next();
            // 如果存在两个相同的值
            if (list.indexOf(item) != list.lastIndexOf(item)) {
                // 移除最后那个相同的值
                System.out.println(list);
                System.out.println("移除重复元素:" + item+" at  "+list.lastIndexOf(item));
                willbelist.remove();
            }
        }
        System.out.println("去重集合:" + list);
    }
}