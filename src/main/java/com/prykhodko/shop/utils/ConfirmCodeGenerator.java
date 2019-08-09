package com.prykhodko.shop.utils;

import java.util.Random;

public class ConfirmCodeGenerator {

    public static String generateCode() {
        int min = 1000;
        int max = 9999;
        int randomNum = new Random().nextInt((max - min) + 1) + min;
        return String.valueOf(randomNum);
    }
}
