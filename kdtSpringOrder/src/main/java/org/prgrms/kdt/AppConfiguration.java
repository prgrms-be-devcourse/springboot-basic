package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//VoucherService, OrderService, OrderRepository, VoucherRepository 의 생성에 대한 책임을 가지게 된다.
//IoC 컨테이너 역할을 하기때문에 객체들의 의존관계 설정, 객체 생성, 파괴를 담당한다.


//@PropertySource("application.properties")
//@PropertySource("application.yml") -> Spring은 PropertySource는 yaml 지원안함. factory 직접구현해야함.(SpringBoot는 지원 함)
@Configuration
@ComponentScan
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

}