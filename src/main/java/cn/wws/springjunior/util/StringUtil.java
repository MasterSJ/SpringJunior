package cn.wws.springjunior.util;

/**  
* @ClassName: StringUtil  
* @Description: �ַ���������
* @author songjun 
* @date 2018��4��11��  
*    
*/
public class StringUtil {
    public static String getClassName(String fullFieldName) {
        int lastPointIndex = fullFieldName.lastIndexOf(".");
        return fullFieldName.substring(0, lastPointIndex);
    }
}
