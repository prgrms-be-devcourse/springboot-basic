package org.prgrms.kdt;

import org.prgrms.kdt.io.MenuController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class KdtApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KdtApplication.class, args);
        MenuController menuController = applicationContext.getBean(MenuController.class);

//        // 등록된 bean의 이름을 get
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//
//        // bean 이름을 출력
//        for(String beanName : beanNames) {
//            System.out.println(beanName);
//        }
//        System.out.println();

        boolean isRepeat = true;

        while (isRepeat) {
            isRepeat = menuController.startMenu();
        }
    }
}
