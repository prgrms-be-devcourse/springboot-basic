package org.prgrms.kdt;

import org.prgrms.kdt.io.IoInteraction;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// service, repository에 대한 생성의 책임을 가진다.
// 각 클래스간의 의존관계를 담당함
// 컴포넌트들을 생성하는 메서드를 만듬
// 빈을 정의한 configuration meta-data(설정클래스)
@Configuration
@ComponentScan
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties // 알려줘야함
public class AppConfiguration {

}
