package com.example.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lss0555 on 2018/8/20/020.
 */
public class MapUtils {
    /**
     * 数组转HashMap
     * @return
     */
    public static HashMap<String,String> strArrToMap(String[] str) {
        HashMap<String,String> map=new HashMap<>();
        for(int i=0;i<str.length;i++){
            map.put(str[i],str[i]);
        }
        return map;
    }


    /**
     * StringList集合转HashMap
     * @return
     */
    public static HashMap<String,String> strListToMap(List<String> list) {
        HashMap<String,String> map=new HashMap<>();
        for(int i=0;i<list.size();i++){
            map.put(list.get(i),list.get(i));
        }
        return map;
    }


    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry e = (Map.Entry) it.next();
            string += "\"" + e.getKey() + "\""+":";
            string += "\"" + e.getValue() + "\""+",";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }




    public static HashMap<String, String> obj2Map(Object obj) {

        HashMap<String, String> map = new HashMap<String, String>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
//            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null){
                    map.put(varName, o.toString());
                    // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                    // 恢复访问控制权限
                    fields[i].setAccessible(accessFlag);
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
