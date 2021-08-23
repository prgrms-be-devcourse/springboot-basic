package org.prgrms.kdt;


import org.prgrms.kdt.Configuration.YamlPropertiesFactory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//설정 메타 데이터(빈을 정의 한 도면인것을)인것을 알려줘야한다
@Configuration
//자동적으로 빈을 찾아주는 어노테이션
@ComponentScan(basePackages ={"org.prgrms.kdt"} )
@PropertySource(value = "application.yaml",factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
//의존 관계를 맺는 클래스
public class AppConfiguration {


}