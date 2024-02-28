package com.yzj.risingpath_zsb_backend.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanCopyUtil {

    public static <S, T> T copy(S source, Class<T> targetClass) {
        T target = createInstanceOfTargetClass(source, targetClass);
        copyMatchingFields(source, target);
        return target;
    }

    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        List<T> copiedList = new ArrayList<>();

        for (S source : sourceList) {
            T target = createInstanceOfTargetClass(source, targetClass);
            copyMatchingFields(source, target);
            copiedList.add(target);
        }

        return copiedList;
    }

    private static <S, T> T createInstanceOfTargetClass(S source, Class<T> targetClass) {
        try {
            return targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <S, T> void copyMatchingFields(S source, T target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();

        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName())
                        && sourceField.getType().equals(targetField.getType())) {
                    try {
                        sourceField.setAccessible(true);
                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }


}