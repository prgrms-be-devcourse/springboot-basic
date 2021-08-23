package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;

@SpringBootApplication
public class KdtApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(KdtApplication.class, args);
        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.info("logger name -> {}",logger.getName());

        logger.info(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
        logger.info(MessageFormat.format("minimunOrderAmount -> {0}", orderProperties.getMinimunOrderAmount()));
        logger.info(MessageFormat.format("description -> {0}", orderProperties.getSupportVendors()));
        logger.info(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

    }

}
