package com.blessing333.springbasic;

import com.blessing333.springbasic.ui.ApacheCliUserInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBasicApplication {

    public static void main(String[] args){
        SpringApplication.run(SpringBasicApplication.class, args);
        App app = new App(new ApacheCliUserInterface());
        app.run();
    }
}
