package cn.wws.springjunior;

import java.io.InputStreamReader;
import java.util.Properties;

/**  
* @ClassName: SjConfigure  
* @Description: 配置信息管理  
* appointedPackageName: 执行由SJ管理的包路径
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class SjConfigure {
    private static Properties props;
    private static String appointedPackageName;
    static {
        readProperties();
        appointedPackageName = props.getProperty("appointedPackageName");
    }
    
    private static void readProperties() {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(
                    SjConfigure.class.getClassLoader().getResourceAsStream("sj.properties"), "UTF-8");
            props.load(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getAppointedPackageName() {
        return appointedPackageName;
    }
}
