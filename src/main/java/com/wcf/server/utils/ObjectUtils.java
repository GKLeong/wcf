package com.wcf.server.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectUtils {
    public static String getPropertyValue(Object entity, String propertyName) {
        // 使用反射获取属性值
        try {
            // 构造getter方法名，假设属性名为propertyName
            String getterMethodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

            // 获取Class对象
            Class<?> clazz = entity.getClass();

            // 获取getter方法
            Method getterMethod = clazz.getMethod(getterMethodName);

            // 调用getter方法获取属性值
            Object value = getterMethod.invoke(entity);

            // 如果属性值不为空，将其转为字符串返回；否则返回空字符串
            return value != null ? value.toString() : "";
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return "";
    }
}
