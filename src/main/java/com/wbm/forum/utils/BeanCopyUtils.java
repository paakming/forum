package com.wbm.forum.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：Ming
 * @Date: 2022/12/9 19:51
 */
public class BeanCopyUtils {

    public static <V>V copyBean(Object source,Class<V> clazz) {
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source,result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list,Class<V> clazz) {
        return list.stream().map(o -> copyBean(o,clazz)).collect(Collectors.toList());
    }
}
