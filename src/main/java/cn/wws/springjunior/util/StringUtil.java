package cn.wws.springjunior.util;

/**  
* @ClassName: StringUtil  
* @Description: 字符串工具类
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class StringUtil {
    public static String getClassName(String fullFieldName) {
        int lastPointIndex = fullFieldName.lastIndexOf(".");
        return fullFieldName.substring(0, lastPointIndex);
    }
}
