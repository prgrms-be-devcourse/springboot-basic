package prgms.springbasic.BlackCustomer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import prgms.springbasic.YamlPropertiesFactory;

@Configuration
@ComponentScan
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class CustomerBlacklistAppConfig {
}
