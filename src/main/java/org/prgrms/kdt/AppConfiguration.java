package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandLineApplication;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.order.domain.Order;
import org.prgrms.kdt.order.repository.OrderRepository;
import org.prgrms.kdt.order.service.OrderService;
import org.prgrms.kdt.voucher.repository.VoucherMemoryRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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

// Spring에게 Configuration Metadata라고 알려줘야함.
@Configuration
public class AppConfiguration {
    // 각각의 Componenet를 맺는 method를 만들어 봅시다.

    // Bean이라는 annotaion을 사용해서 bean을 정의합니다.
    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherMemoryRepository();
        /** 나중 강의를 위해에 남겨둠
         return new VoucherRepository() {

        @Override public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
        }

        @Override public List<Voucher> find() {
        return new ArrayList<>();
        }

        @Override public void create(Voucher voucher) {

        }
        };
         */
    }

    @Bean
    public Console console() {
        return new Console();
    }

    @Bean
    public CommandLineApplication commandLineApplication(Console console, ApplicationContext applicationContext) {
        return new CommandLineApplication(console, applicationContext);
    }
}
