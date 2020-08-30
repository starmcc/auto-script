package com.starmcc.auto.script;


import com.starmcc.qmframework.config.QmFrameworkApplication;
import com.starmcc.qmframework.exception.QmExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * @Author: qm
 * @Date: 2020/8/29 23:11
 */
@SpringBootApplication
@Import({
        QmFrameworkApplication.class, // qm-framework 主要依赖配置
        QmExceptionHandler.class, // qm-framework 引入全局异常捕获
})
public class AutoScriptApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AutoScriptApplication.class);
        builder.headless(false).run(args);
    }



}
