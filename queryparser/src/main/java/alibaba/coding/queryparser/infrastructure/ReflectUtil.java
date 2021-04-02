package alibaba.coding.queryparser.infrastructure;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;

public final class ReflectUtil {

    @SneakyThrows
    public static Object invokeGetterMethod(Class<?> pojoClass, Object pojo, String fieldName) {
        String methodName = "get" + StringUtil.capitalize(fieldName);
        try {
            return pojoClass.getMethod(methodName).invoke(pojo);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new Exception("反射调用 Pojo 字段 Getter 方法出错");
        }
    }
}