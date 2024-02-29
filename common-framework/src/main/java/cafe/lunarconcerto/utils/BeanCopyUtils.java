package cafe.lunarconcerto.utils;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() { }


    public static <O, T> T copyBean(O source, Class<T> clazz){
        try {
            // 创建目标对象
            T result = clazz.getConstructor().newInstance();

            // 实现属性拷贝
            BeanUtils.copyProperties(source, result);

            // 返回结果
            return result ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static <O, T> List<T> copyBeanList(List<O> objectList, Class<T> tClass){
        return objectList.stream()
                .map(o -> copyBean(o, tClass))
                .collect(Collectors.toCollection(() -> new ArrayList<>(objectList.size())))
                ;
    }

}
