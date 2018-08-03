package cn.wws.springjunior.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wws.springjunior.itf.SjInputOutputInterface;

/**  
* @ClassName: DefultInputOutput  
* @Description: 默认输入输出实现类
* @author songjun 
* @date 2018年8月3日  
*    
*/
public class DefultInputOutput implements SjInputOutputInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefultInputOutput.class);
    
    @Override
    public boolean input(String content) {
        return true;
    }

    @Override
    public boolean output(String content) {
        LOGGER.info(content);
        return true;
    }

}
