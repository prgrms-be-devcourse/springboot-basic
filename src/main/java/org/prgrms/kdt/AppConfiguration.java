package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


// 오더서비스, 오더레포지터리, 바우처서비스, 바우처 레포지토리의 생성 대한 책임을 갖게 되었음
// 의존관계를 맺는걸 담당하게됨( wirering)
// ComponentScan을 달아줌으로서 해당 파일이 위치한 패키지 내를 전부 찾는다
@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.order", "org.prgrms.kdt.voucher", "org.prgrms.kdt.assignments"})
// 또는 @ComponentScan(basePackageClasses = {Order.class, Voucher.class}) 를 사용해서 해당 클래스가 있는 패키지를 경로로 하게됨
// 만약 pom.xml의 Dependency를 통해서 다른 프로젝트를 포함하게 될 때 공통된 패키지명이나 다른 내용이 포함될 수 있기 때문에 excludeFilters를 통해서 제외 할 수 있다
//@PropertySource("application.properties")
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

}







    /*
    // 인터페이스이기 때문에 내용 구현하여 리턴
    // Anontation을 달아줌으로 Configuration MetaData가 된다
    // Annotation을 인터페이스가 아닌 구현체에서 선언해주어야한
    @Bean
   public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }

            @Override
            public Voucher insert(Voucher voucher) {
                return null;
            }
        };
    }
    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public Order insert(Order order) {
            }
        };
    }
}

     */
