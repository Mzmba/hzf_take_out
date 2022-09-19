package com.ka.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 837312868@qq.com
 * @version : 1.0
 * @Project : reggie
 * @Package : com.ka.test
 * @createTime : 2022/9/17 19:28 星期六
 * @Description :
 */
@Data
public class 中文测试类 {
    private static String name = "中文测试类1";

    @Test
    public void 中(){
        System.out.println(this.getClass());
        System.out.println(JSON.toJSONString(this.toString()));
    }
}
