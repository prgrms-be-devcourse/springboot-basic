package org.programmers.applicationcontext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Optional;
import java.util.UUID;

/*OrderService, VoucherService, OrderRepository, VoucherRepository의 생성에 대한 책임을 갖고 있고,
* 각각의 서비스에 대한 의존성 관리를 담당한다*/

@Configuration // Configuration Metadata임을 스프링에게 알려주기 위해 붙인 어노테이션이다!
public class AppConfiguration { // '설정 메타데이터' 클래스, 예전 OrderContext

    @Bean // 각 메소드에 @Bean을 붙인다 -> AppConfiguration은 Bean을 정의한 Configuration Metadata가 된다
    public VoucherRepository voucherRepository(){
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public void insert(Voucher voucher) {

            }
        };
    }

    @Bean
    public OrderRepository orderRepository(){
        return new OrderRepository() {
            @Override
            public void insert(Order order) {

            }
        };
    }

    @Bean
    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }


    @Bean
    public  OrderService orderService(){
        return new OrderService(voucherService(), orderRepository());
    }
}
