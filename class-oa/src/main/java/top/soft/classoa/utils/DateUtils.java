package top.soft.classoa.utils;


import java.util.Date;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/12/7 19:50
 */
public class DateUtils {
    public static long getDiffHours(Date startTime, Date endtIME){
       long diff = endtIME.getTime() - startTime.getTime();
       return diff / (1000 * 60 * 60);
    }
}
