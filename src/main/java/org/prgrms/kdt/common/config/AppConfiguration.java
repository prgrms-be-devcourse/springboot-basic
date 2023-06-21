package org.prgrms.kdt.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(
        basePackages = { "org.prgrms.kdt" }
)
@PropertySource(value = "application.yaml", factory = YamlPropertyFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

//    @Bean(initMethod = "init")
//    public BeanOne beanOne() {
//        return new BeanOne();
//    }
}

//class BeanOne implements InitializingBean {
//    public void init() {
//        System.out.println("[BeanOne] init called!");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception{
//        System.out.println("[BeanOne]afterPropertiesSet init called!");
//    }
//}