package org.prgrms.kdt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 7:51 오후
 */
@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.kdt.voucher", "org.prgrms.kdt.order", "org.prgrms.kdt.config"}
)
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class )
@EnableConfigurationProperties
public class AppConfiguration {



}