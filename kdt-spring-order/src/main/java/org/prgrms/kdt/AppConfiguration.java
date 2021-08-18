package org.prgrms.kdt;

import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan //패키지 기준으로
public class AppConfiguration {





//    @Bean
//    public VoucherRepository voucherRepository(){
//        return new VoucherRepository() {
//            @Override
//            public Optional<Voucher> findById(UUID voucherId) {
//                return Optional.empty();
//            }
//        };
//    }
//
//    @Bean
//    public OrderRepository orderRepository(){
//        return new OrderRepository() {
//            @Override
//            public Order insert(Order order) {
//
//            }
//        };
//    }


//    @Bean
//    public VoucherService voucherService(){
//        return new VoucherService(voucherRepository());
//    }

    // voucherService는 voucherRepository를 필요로 하므로 다음과 같이 쓸수도 있음
    // 메소드에서 의존이 되어지는 해당 클래스를 작성하게되면 메소드의 매개변수로 정의해주면
    // 스프링이 빈에대한 객체를 만들때 찾아서 전달해 줌



//    @Bean
//    public OrderService orderService(){
//        return new OrderService(voucherService(),orderRepository());
//    }



    //Service 어노테이션으로 인해 자동으로 Bean이 등록된다. 아래는 필요없어짐
//짐   @Bean
//    public VoucherService voucherService(VoucherRepository voucherRepository){
//        return new VoucherService(voucherRepository);
//    }
//
//    @Bean
//    public OrderService orderService(VoucherService voucherService,OrderRepository orderRepository ){
//        return new OrderService(voucherService,orderRepository);
//    }
}
