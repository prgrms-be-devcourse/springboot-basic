package com.example.voucherproject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
public class VoucherProjectApplication {
    public static final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoucherProjectApplication.class);

    public static void main(String[] args) {
        applicationContext.getBean(AllService.class).run();
        applicationContext.close();
    }
}
