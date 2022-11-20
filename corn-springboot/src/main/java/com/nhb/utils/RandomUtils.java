package com.nhb.utils;

import java.util.Random;

/**
 * @author 大只
 * @date 2022-11-20
 */
public class RandomUtils {

    public static String getRandom(int size){
        Random r = new Random();
        String res = "";
        for (int i = 0; i < size; i++) {
            int num = r.nextInt(9); // 生成[0,9]区间的整数
            System.out.print(num);
            res += num;
        }

        return res;
    }
}
