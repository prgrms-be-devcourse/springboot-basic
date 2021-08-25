package org.prgms;


import org.prgms.black.BlackProperties;
import org.prgms.io.Io;
import org.prgms.order.Order;
import org.prgms.voucher.Voucher;
import org.prgms.black.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackageClasses = {Order.class, Voucher.class, Io.class, BlackProperties.class})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties

public class AppConfiguration {

}
