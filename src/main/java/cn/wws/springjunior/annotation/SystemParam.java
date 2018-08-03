package cn.wws.springjunior.annotation;

import java.util.ArrayList;
import java.util.List;

public class SystemParam {
    public static ArrayList<Class> getAllClassByInterface(Class clazz, List<Class<?>> appoitedAllClassList) {
        ArrayList<Class> list = new ArrayList<>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (int i = 0; i < appoitedAllClassList.size(); i++) {
                    /**
                     * 判断是不是同一个接口
                     */
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (clazz.isAssignableFrom(appoitedAllClassList.get(i))) {
                        if (!clazz.equals(appoitedAllClassList.get(i))) {
                            // 自身并不加进去
                            list.add(appoitedAllClassList.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        } else {
            // 如果不是接口，则获取它的所有子类
            try {
                /**
                 * 循环判断路径下的所有类是否继承了指定类 并且排除父类自己
                 */
                for (int i = 0; i < appoitedAllClassList.size(); i++) {
                    if (clazz.isAssignableFrom(appoitedAllClassList.get(i))) {
                        if (!clazz.equals(appoitedAllClassList.get(i))) {
                            // 自身并不加进去
                            list.add(appoitedAllClassList.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("出现异常");
            }
        }
        return list;
    }
    
    
}
