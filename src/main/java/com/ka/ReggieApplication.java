package com.ka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : 837312868@qq.com
 * @version : 1.0
 * @Project : reggie
 * @Package : com.ka
 * @createTime : 2022/9/15 17:27 星期四
 * @Description :
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement (proxyTargetClass = true)
@EnableCaching
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功！！！");
    }
}
