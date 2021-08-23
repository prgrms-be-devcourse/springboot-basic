package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan(basePackages = {"org.prgrms.kdt.order","org.prgrms.kdt.voucher"}) // 베이스 패키지 설정 , 베이스 패키지만 스캔
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class}) // 클래스가 속한 패키지를 지명할 수 있음
@ComponentScan(basePackages = {"org.prgrms.kdt.*"})//,"org.prgrms.kdt.voucher","org.prgrms.kdt.configuration"})
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MemoryVoucherRepository.class)}) // 반대로 제거 가능
//@PropertySource("application.properties")
@PropertySource(value = "application.yaml",factory = YamlPropertiesFactory.class) //스프링 프레임워크는 지원하지 않아서 팩토리를 구현해야함, 부트는 지원함
@EnableConfigurationProperties // springboot의 해당기능을 사용하기 위해서
public class AppConfiguration {
// 설정 클래스를 용도에 맞게 분류를 해서 사용함 org.prgrms.kdt.configuration

    //D3 실습
//    @Bean(initMethod = "init")
//    public BeanOne beanOne() {
//        return new BeanOne();
//    }

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

//
//    @Bean
//    public VoucherService voucherService(){
//
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

class BeanOne implements InitializingBean {
    public void init(){
        System.out.println("[BeanOne] init called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[BeanOne] afterPropertiesSet called!");
    }
}