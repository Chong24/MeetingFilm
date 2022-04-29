package com.mooc.meetingfilm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础工具类：
 * @author wang
 * @create 2022-04-19
 */
public class ToolUtils {

    //即基础工具类不允许实例
    private ToolUtils(){

    }

    /**
     * 输入的字符串是否为空；由于不能实例化，所以只能通过声明为static,可以直接用类名
     * @param str
     * @return
     */
    public static boolean strIsNull (String str){
        if (str == null || str.trim().length() == 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 输入的字符串不为空
     * @param str
     * @return
     */
    public static boolean strIsNotNul (String str){
        if (strIsNull(str)){
            return false;
        }else {
            return true;
        }
    }

    //判断数字正则表达式
    private static final Pattern pattern = Pattern.compile("[0-9]*");

    //检查字符串是不是数字类型：因为我们JavaBean中属性声明的都是String类型，所以需要判断
    public static boolean checkInt(String str){
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()){
            return false;
        }else{
            return true;
        }
    }

    //字符串转换为int类型
    public static Integer str2Int(String str){
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return 0;
        }else{
            return Integer.parseInt(str);
        }
    }

    //字符串转换为LocalDateTime, LocalDateTime同时含有年月日时分秒的日期对象
    public static LocalDateTime str2LocalDateTime(String dateStr){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(dateStr, df);
        return ldt;
    }
}
