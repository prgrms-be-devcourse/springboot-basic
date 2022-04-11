package org.prgrms.kdt.config;

 // 주요 객체의 생성과 객체간 관계 설정을 담당하는 클래스
 // service, repository 객체 생성 + service, repository 간의 의존관계를 맺는 역할
 // OrderService & OrderRepository / OrderService & VoucherService
 // 실제 객체의 전달은 OrderContext 가 담당
 // Bean 을 정의한 Configuration Metadata 설정 클래스

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan( basePackages = {
                // "org.prgrms.kdt.voucher",
                // "org.prgrms.kdt.order",
                // "org.prgrms.kdt.config",
                // "org.prgrms.kdt.blacklist",
                // "org.prgrms.kdt.customer"} )
                "org.prgrms.kdt.config"} )
// @PropertySource(value = "application.yml", factory = YmlPropertiesFactory.class)

@EnableConfigurationProperties
public class AppConfiguration { }