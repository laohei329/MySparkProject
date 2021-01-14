package com.sun.java;

import java.util.ArrayList;
import java.util.Collections;

public class ListTest {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4, 5, 6};
        ArrayList list = new ArrayList<>();
        System.out.println(Collections.addAll(list,ints));
        for (Object o : list) {
            System.out.println(o);
        }


    }
}
