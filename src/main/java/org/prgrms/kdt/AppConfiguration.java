package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

/**
 * OrderContext는 주문에 대한 전반적인 도메인 객체에 대한 생성을 책임지고 있음.
 * voucherservice, orderService, orderRepository, voucherRepository 생성에 대한 책임을 가짐
 * 각각의 repository와 service간의 의존관계를 맺는 것을 담당.
 * <p>
 * OrderContext = IoC Container
 * - IoC Container에서는 개별 객체들의 의존 관계 설정이 이뤄지고
 * - 객체들에 대해서 생성과 파괴에 대해 관장합니다.
 * <p>
 * 런타임에 여러 객체들간의 Runtime Depdendency를 맺게해주면서 느슨한 결합도를 만들어 주고 있습니다.
 * - 직접 생성하지 않고 이러한 Container, Context에 그러한 권한을 위임해버리면 결합도가 생기지 않아요
 * - Tombi의 스프링을 꼭 읽으세용 ㅋㅋ
 */

/**
 * Enviornment
 * - Spring Application이 처한 상황 및 시공간을 등 환경을 말함.
 * - 속성은 property 파일, jvm sys.pro, os env, spring.property, yaml
 * -
 */
@Configuration
@ComponentScan(basePackages = {
        "org.prgrms.kdt.order",
        "org.prgrms.kdt.voucher",
        "org.prgrms.kdt.configuration",
        "org.prgrms.kdt.command",
        "org.prgrms.kdt.customer",
})
// 기본적으로 spring boot는 yaml 을 지원하는데, @PropertySource는 yaml을 지원하지 않습니다 ㅠㅠ 그래서 factory로 만들어서 줍시다
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class) // property를 적용
@EnableConfigurationProperties // OrderProperties사용하
public class AppConfiguration {
}

