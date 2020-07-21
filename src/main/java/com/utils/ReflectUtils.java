/**
 * 文件名：ReflectUtils.java
 * 
 *北京中油瑞飞信息技术有限责任公司(http://www.richfit.com)
 * Copyright © 2017 Richfit Information Technology Co., LTD. All Right Reserved.
 */
package com.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision: 4669 $</li>
 * <li>$Date: 2017-06-07 11:11:35 +0800 (Wed, 07 Jun 2017) $</li>
 * 
 * @version 1.0
 */
public class ReflectUtils {

    protected static Log log = LogFactory.getLog( ReflectUtils.class);

    /**
     * 利用反射将map集合封装成bean对象
     * by CHENYB date 2019-05-29
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
        Object obj = clazz.newInstance();
        if (map != null && !map.isEmpty() && map.size() > 0) {
            for (Entry<String, Object> entry : map.entrySet()) {
                String propertyName = entry.getKey(); 	// 属性名
                Object value = entry.getValue();		// 属性值
                if (null == value){
                    continue;
                }
                String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                Field field = getClassField(clazz, propertyName);	//获取和map的key匹配的属性名称
                if (field == null){
                    continue;
                }
                Class<?> fieldTypeClass = field.getType();
                value = convertValType(value, fieldTypeClass);
                try {
                    if (StringUtils.isNotBlank( setMethodName )&& setMethodName.toUpperCase().contains( "SERIALVERSIONUID" ))
                        continue;
                    clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) obj;
    }

    /**
     * 根据给定对象类匹配对象中的特定字段
     * @param clazz
     * @param fieldName
     * @return
     */
    private static Field getClassField(Class<?> clazz, String fieldName) {
        if (Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();	//如果该类还有父类，将父类对象中的字段也取出
        if (superClass != null) {						//递归获取
            return getClassField(superClass, fieldName);
        }
        return null;
    }

    /**
     * 将map的value值转为实体类中字段类型匹配的方法
     * @param value
     * @param fieldTypeClass
     * @return
     */
    private static Object convertValType(Object value, Class<?> fieldTypeClass) {
        Object retVal = null;

        if (Long.class.getName().equals(fieldTypeClass.getName())
                || long.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldTypeClass.getName())
                || int.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldTypeClass.getName())
                || float.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldTypeClass.getName())
                || double.class.getName().equals(fieldTypeClass.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else {
            retVal = value;
        }
        return retVal;
    }

    /**
     * 利用反射将map集合封装成bean对象
     * by CHENYB date 2019-05-29
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, String> objToMap(Object obj) {

        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();	// 获取f对象对应类中的所有属性域
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            //varName = varName.toLowerCase();					// 将key置为小写，默认为对象的属性
            try {
                boolean accessFlag = fields[i].isAccessible();	// 获取原来的访问控制权限
                fields[i].setAccessible(true);					// 修改访问控制权限
                Object o = fields[i].get(obj);					// 获取在对象f中属性fields[i]对应的对象中的变量
                if (o != null){
                    map.put(varName, o.toString());
                }
                fields[i].setAccessible(accessFlag);			// 恢复访问控制权限
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

    /**
     * map Value String 转 Objcet
     * @param map
     * @return
     */
    public static HashMap<String, Object> mapStrToHashMapObj(Map<String, String> map){
        HashMap<String, Object> resultMap = new HashMap<>();
        for (Entry<String, String> entry : map.entrySet()) {
            resultMap.put( entry.getKey(),entry.getValue());
        }
        return resultMap;
    }

    /**
     * map Value Objcet 转 String
     * @param map
     * @return
     */
    public static HashMap<String, String> mapObjToHashMapStr(Map<String, Object> map){
        HashMap<String, String> resultMap = new HashMap<>();
        for (Entry<String, Object> entry : map.entrySet()) {
            resultMap.put( entry.getKey(),entry.getValue().toString());
        }
        return resultMap;
    }

    /**
     * @param clazz
     * @return
     * @author created by figo
     */
    public static Map<String, Field> getDeclaredFieldsMap(Class<?> clazz) {
        Map<String, Field> filesMap = new HashMap<>();
        if (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                filesMap.put(f.getName(), f);
            }
        }
        return filesMap;
    }

    /**
     * @param paramMap
     * @param obj
     * @author created by figo
     */
    public static void bindingPropertyValue(Map<String, String> paramMap, Object obj) {
        if (MapUtils.isEmpty(paramMap) || obj == null) {
            return;
        }
        Map<String, Field> fieldsMap = getDeclaredFieldsMap(obj.getClass());
        for (Entry<String, String> entry : paramMap.entrySet()) {
            if (fieldsMap.containsKey(entry.getKey())) {
                Field field = fieldsMap.get(entry.getKey());
                Object value = entry.getValue();
                if (field.getType().getName().equals("java.lang.Integer")) {
                    value = NumberUtils.toInt(entry.getValue());
                } else if (field.getType().getName().equals("java.lang.Long")) {
                    value = NumberUtils.toLong(entry.getValue());
                } else if (field.getType().getName().equals("java.util.Date")) {
                    value = DateStringCalcUtils.stringToDateByGeneralYMDHMS(entry.getValue());
                }
                try {
                    PropertyUtils.setProperty(obj, entry.getKey(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("bindingPropertyValue error! msg is:" + LogUtils.getErrorMsg(e));
                }
            }
        }
    }
    public static void bindingPropertyValueFromObject(Map<String, Object> paramMap, Object obj) {
        if (MapUtils.isEmpty(paramMap) || obj == null) {
            return;
        }
        Map<String, Field> fieldsMap = getDeclaredFieldsMap(obj.getClass());
        for (Entry<String, Object> entry : paramMap.entrySet()) {
            if (fieldsMap.containsKey(entry.getKey())) {
                Field field = fieldsMap.get(entry.getKey());
                Object value = entry.getValue();
                if (field.getType().getName().equals("java.lang.Integer")) {
                    value = NumberUtils.toInt(entry.getValue().toString());
                } else if (field.getType().getName().equals("java.lang.Long")) {
                    value = NumberUtils.toLong(entry.getValue().toString());
                } else if (field.getType().getName().equals("java.util.Date")) {
                    value = DateStringCalcUtils.stringToDateByGeneralYMDHMS(entry.getValue().toString());
                }
                try {
                    PropertyUtils.setProperty(obj, entry.getKey(), value);
                } catch (Exception e) {
                    log.error("bindingPropertyValue error! msg is:" + LogUtils.getErrorMsg(e));
                }
            }
        }
    }
}
