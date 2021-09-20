package org.programmers.applicationcontext.config;
import org.programmers.applicationcontext.order.Order;
import org.programmers.applicationcontext.order.OrderRepository;
import org.programmers.applicationcontext.order.OrderService;
import org.programmers.applicationcontext.voucher.Voucher;
import org.programmers.applicationcontext.voucher.VoucherRepository;
import org.programmers.applicationcontext.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Optional;
import java.util.UUID;


@Configuration // Configuration Metadata임을 스프링에게 알려주기 위해 붙인 어노테이션이다!
public class AppConfiguration {


    @Bean // 각 메소드에 @Bean을 붙인다 -> AppConfiguration은 Bean을 정의한 Configuration Metadata가 된다
    public VoucherRepository voucherRepository(){
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }

    @Bean // OrderTester에서  OrderService를 bean으로 등록하기 위해 작성
    public OrderService orderService(){
        return new OrderService(new OrderRepository() {
            @Override
            public void insert(Order order) {
            }
        });
    }
}
