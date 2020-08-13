package com.swagger2word.utils;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/13 13:10
 **/

public class MenuUtils {
    public static Integer count = 0;
    public static String menuStr = "null";

    public static boolean isMenu(String tags) {
        if (menuStr.equals(tags)) {
            count++;
        } else {
            menuStr = tags;
            count = 0;
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }
}
