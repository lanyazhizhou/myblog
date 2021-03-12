package com.zyd.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 程序启动类
 *
 * @author lanyazhizhou@gmail.com
 * @version 1.0
 * @website https://lanyazhizhou.com
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@SpringBootApplication
@ServletComponentScan
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
