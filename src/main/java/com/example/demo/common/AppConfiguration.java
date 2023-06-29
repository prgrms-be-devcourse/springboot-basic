package com.example.demo.common;

import com.example.demo.common.io.impl.ConsoleInput;
import com.example.demo.common.io.impl.ConsoleOutput;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = "com.example.demo.voucher")
public class AppConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yaml"), new ClassPathResource("application-dev.yaml"));

        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());

        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput();
    }

}
