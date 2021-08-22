package org.prgrms.kdt;

import org.hibernate.annotations.Filter;
import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.domain.OrderProperties;
import org.prgrms.kdt.test.OrderTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@springbootApplication 밑에 component 스캔을 하게 되면 아래 설정한 범위까지만 빈으로 주입받는다.
//@SpringBootApplication
@ComponentScan( basePackages = {"org.prgrms.kdt.domain",
        "org.prgrms.kdt.repository",
        "org.prgrms.kdt.service",
        "org.prgrms.kdt.factory",
        "org.prgrms.kdt.fileutil"}
) //controller를 제외해야한다 -> 안그럼 밑에 실행이 안됨
public class KdtApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);


        var applicationContext = SpringApplication.run(KdtApplication.class, args);
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.warn("logger name -> {}",logger.getName()); // {}안에 값이 치환되서 출력된다.
        logger.warn("minimumOrderAmount -> {}",orderProperties.getMinimumOrderAmount());
        logger.warn("version -> {}",orderProperties.getVersion());
        logger.warn("supportVendors -> {}",orderProperties.getSupportVendors());
        logger.warn("description -> {}",orderProperties.getDescription());

//        SpringApplication.run(KdtApplication.class, args);




    }

}
