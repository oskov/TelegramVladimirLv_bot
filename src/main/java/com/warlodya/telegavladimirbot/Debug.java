package com.warlodya.telegavladimirbot;

public class Debug {
    public static void main(String[] args) {
        rec(4);
    }

    private static void rec(int i) {
        if (i < 0) {
            return;
        }
        rec(i - 1);
        rec(i - 2);

    }
}
