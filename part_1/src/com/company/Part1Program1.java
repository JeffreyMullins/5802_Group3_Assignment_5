package com.company;

import java.math.BigDecimal;


public class Part1Program1 {

    // Just a simple class with functions that are designed to trigger specific SpotBugs warnings. The constructor calls the functions to demonstrate the bugs.
    public static void Part1Program1(String[] args) {
        part_1_print();
        badPractice();
        correctnessBug();
        multithreadedBug();
        performanceBug();
        internationalizationBug();
        dodgyBug();
    }

    public static void part_1_print() {
        // just testing that the program runs without SpotBugs bugs
        System.out.println("hello world");
    }

    // RV_RETURN_VALUE_IGNORED
    public static void badPractice() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("2");
        a.add(b); // RV_RETURN_VALUE_IGNORED
    }

    // NP_NULL_ON_SOME_PATH
    public static void correctnessBug() {
        String s = null;
        if (Math.random() > 0.5) {
            s = "hello";
        }
        System.out.println(s.length()); // NP_NULL_ON_SOME_PATH
    }

    private int x;

    // IS2_INCONSISTENT_SYNC
    public void setX(int x) {
        this.x = x; // IS2_INCONSISTENT_SYNC
    }

    public synchronized int getX() {
        return x;
    }

    public static void multithreadedBug() {
        Part1Program1 p = new Part1Program1();
        p.setX(5);
    }

    // DM_STRING_CTOR
    public static void performanceBug() {
        String s = new String("hello"); // DM_STRING_CTOR
    }


    // I18N_LOCALE
    public static void internationalizationBug() {
        String value = "straße";
        String upper = value.toUpperCase(); // I18N_LOCALE
        System.out.println(upper);
    }

    // DLS_DEAD_LOCAL_STORE
    public static void dodgyBug() {
        int x = 1;
        x = 2; // DLS_DEAD_LOCAL_STORE
        System.out.println(x);
    }

}
