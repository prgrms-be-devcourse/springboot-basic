package org.prgrms.kdtspringhw.configuration;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdtspringhw.*"})
//@PropertySource(value = "application.yaml",factory = YamlPropertiesFactory.class) //스프링 프레임워크는 지원하지 않아서 팩토리를 구현해야함, 부트는 지원함
//@EnableConfigurationProperties // springboot의 해당기능을 사용하기 위해서
public class AppConfiguration {
}
