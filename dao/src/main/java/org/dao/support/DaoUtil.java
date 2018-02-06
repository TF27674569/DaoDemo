package org.dao.support;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2017/10/23
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */

public class DaoUtil {

    /**
     * 根据类型确定建表所需要的type
     */
    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = " text";
        } else if (type.contains("int")) {
            value = " integer";
        } else if (type.contains("boolean")) {
            value = " boolean";
        } else if (type.contains("float")) {
            value = " float";
        } else if (type.contains("double")) {
            value = " double";
        } else if (type.contains("char")) {
            value = " varchar";
        } else if (type.contains("long")) {
            value = " long";
        }
        return value;
    }

    /**
     * 获取表名
     */
    public static <T> String getTabName(Class<T> clazz) {
        return clazz.getSimpleName();
    }


    /**
     * 获取列名
     */
    public static String getColumnName(Field field) {

        return field.getName();
    }

    // 首字母大写
    public static String capitalize(String string) {

        if (!TextUtils.isEmpty(string)) {
            return string.substring(0, 1).toUpperCase(Locale.US) + string.substring(1);
        }
        return string == null ? null : "";
    }

}
