package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.app.VoucherApplication;
import com.prgrms.springbootbasic.common.config.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringbootBasicApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootBasicApplication.class, args);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherApplication console = applicationContext.getBean(VoucherApplication.class);
        console.runLifecycle();
    }

}
