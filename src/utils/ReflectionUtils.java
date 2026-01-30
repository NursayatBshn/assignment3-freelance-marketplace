package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public static void inspectEntity(Class<?> clazz) {
        System.out.println("Reflection Analysis for: " + clazz.getSimpleName());

        System.out.println("Fields:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("- " + field.getName() + " (" + field.getType().getSimpleName() + ")");
        }

        System.out.println("Methods:");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("- " + method.getName() + " [Returns: " + method.getReturnType().getSimpleName() + "]");
        }
        System.out.println();
    }
}