package org.prgms.w3d1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class W3D1Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(W3D1Application.class, args);
    }
}
