package org.prgrms.dev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * - 주요 객체에 대해서 생성과 관계 설정을 하는 클래스
 * - service, repository 에 대한 생성의 책임을 가진다.
 * - 각각의 service 와 repository 간의 의존 관계를 맺는 역할
 * <p>
 * - OrderService ↔️ OrderRepository 의 관계와
 *   OrderService ↔️ VoucherService  의 관계를
 *   OrderContext 에서 실제 객체를 전달함으로써 만든 것
 * <p>
 * - Bean을 정의한 Configuration Metadata
 * - 설정 클래스 !!
 */

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.dev.voucher", "org.prgrms.dev.order", "org.prgrms.dev.config", "org.prgrms.dev.customer" }
)
public class AppConfiguration {
}
