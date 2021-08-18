package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KdtSpringOrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtSpringOrderApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KdtSpringOrderApplication.class, args);
        OrderProperties orderProperties = context.getBean(OrderProperties.class);

        logger.error(logger.getName());
        logger.warn(orderProperties.getVersion());
        logger.warn(String.valueOf(orderProperties.getMinimumOrderAmount()));
        logger.warn(String.valueOf(orderProperties.getSupportVendors()));
        logger.warn(orderProperties.getDescription());
    }

}
