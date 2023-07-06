package com.prgms.springbootbasic;

import com.prgms.springbootbasic.util.YamlPropertyFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource(value = "application.yaml", factory = YamlPropertyFactory.class)
public class AppConfig {

}
