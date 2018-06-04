package cn.wws.springjunior.ioc;

import java.util.HashMap;

/**  
* @ClassName: SjClassMapped  
* @Description: SjClass标记的别名跟类全称映射关系管理类
* @author songjun 
* @date 2018年6月4日  
*    
*/
public class SjClassMapped {
    /**  
    * @Fields 存SjClass标记的别名跟类全称映射关系  
    */  
    private static HashMap<String, String> fullNameMappedAlias = new HashMap<String, String>();
    
    /**    
    * @Description: 添加映射关系. 
    * @author songjun  
    * @date 2018年6月4日   
    * @param fullName
    * @param alias
    */ 
    public static void putMappedRelation(String fullName, String alias) {
        fullNameMappedAlias.put(fullName, alias);
    }
    
    /**    
    * @Description: 获取映射关系. 
    * @author songjun  
    * @date 2018年6月4日   
    * @param fullName
    * @return
    */ 
    public static String getFullName(String fullName) {
        return fullNameMappedAlias.get(fullName);
    }
}
