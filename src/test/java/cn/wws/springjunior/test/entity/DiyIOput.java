package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.itf.SjInputOutputInterface;

public class DiyIOput implements SjInputOutputInterface {

    @Override
    public boolean input(String content) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean output(String content) {
        System.out.println("自定义输出：" + content);
        return false;
    }

}
