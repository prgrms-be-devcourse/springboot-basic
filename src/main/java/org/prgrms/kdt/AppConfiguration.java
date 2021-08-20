package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

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

@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.order", "org.prgrms.kdt.voucher"})
public class AppConfiguration {

    @Bean
    public Console console() {
        return new Console();
    }

    @Bean
    public CommandLineApplication commandLineApplication(Console console, ApplicationContext applicationContext) {
        return new CommandLineApplication(console, applicationContext);
    }
}
