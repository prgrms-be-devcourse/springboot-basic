package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yhh1056
 * Date: 2021/08/22 Time: 10:34 오후
 */

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order"}
)
public class KdtApplication {

    private static final Logger logger = LoggerFactory.getLogger(KdtApplication.class);

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(KdtApplication.class, args);
        var orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.info("version = {}", orderProperties.getVersion());
        logger.info("minimumOrderAmount = {}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendors = {}", orderProperties.getSupportVendors());
        logger.info("description = {}", orderProperties.getDescription());
    }
}
