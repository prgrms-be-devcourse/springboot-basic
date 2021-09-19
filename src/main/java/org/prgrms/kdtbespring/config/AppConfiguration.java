package org.prgrms.kdtbespring.config;

import org.prgrms.kdtbespring.IgnoreComponentScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


/**
 * VoucherService, VoucherRepository
 * OrserService, OrderRepository
 * 에 대한 생성에 대한 책임을 갖게 됨.
 * 그리고 각각의 의존 관계를 맺는것을 담당하게 됨.
 *
 * @Configuration의 Bean이 붙은 메서드는 내부적으로 호출되어 리턴타입의 객체가 ApplicationContext(IoC컨테이너가 관리하게 된다.)
 */
@Configuration
@ComponentScan(
        // basePackageClasses = {Order.class, OrderRepository.class, OrderService.class}
        basePackages = "org.prgrms.kdtbespring",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = IgnoreComponentScan.class)}
)
public class AppConfiguration {

    @Bean(initMethod = "init")
    public BeanOne beanOne(){
        return new BeanOne();
    }

}
class BeanOne implements InitializingBean {

    private void init() {
        System.out.println("[BeanOne] init Method called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[BeanOne] afterPropertiesSet called!");
    }
}