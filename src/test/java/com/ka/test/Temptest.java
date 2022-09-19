package com.ka.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 837312868@qq.com
 * @version : 1.0
 * @Project : reggie
 * @Package : PACKAGE_NAME
 * @createTime : 2022/9/16 11:15 星期五
 * @Description :
 */

@SpringBootTest
public class Temptest {

    @Value("${reggie.path}") 
    private String basePath;

    @Value("${reggie.path2}")
    private String basePath2;

    @Value("${reggie.path3}")
    private String basePath3;

    @Value("${reggie.path4}")
    private String basePath4;


    @Test
    public void testSave() throws IOException {
        File file = new File(basePath);
        fun1(file);

        file= new File(basePath2);
        fun1(file);

        file= new File(basePath3);
        fun1(file);

        file= new File(basePath4);
        fun1(file);
    }

    private void fun1(File fileandjustamulu) throws IOException {
        System.out.println("fileandjustamulu" + fileandjustamulu);
        System.out.println("file.getPath()" + fileandjustamulu.getPath());
        System.out.println("file.getAbsolutePath()" + fileandjustamulu.getAbsolutePath());
        System.out.println("file.getCanonicalPath()" + fileandjustamulu.getCanonicalPath());
        System.out.println("file.getAbsoluteFile()" + fileandjustamulu.getAbsoluteFile());
        System.out.println("file.getCanonicalFile()" + fileandjustamulu.getCanonicalFile());
        System.out.println("------");
    }

    //使用

    @Test
    public void testlambda(){
        LambdaClass.forEg();
    }
}
@FunctionalInterface
interface LambdaInterface {
    void f();
}

class LambdaClass {
    public static void forEg() {
        lambdaInterfaceDemo(new LambdaInterface() {
            @Override
            public void f() {
                System.out.println("自定义函数式接口呀");
            }
        });
    }
    //函数式接口参数
    static void lambdaInterfaceDemo(LambdaInterface i){
        i.f();
    }
}