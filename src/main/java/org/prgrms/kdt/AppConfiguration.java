package org.prgrms.kdt;

import org.prgrms.kdt.repository.OrderRepository;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

@Configuration
@ComponentScan
public class AppConfiguration {

    @Bean(initMethod = "init")
    public BeanOne beanOne() {
        return new BeanOne();
    }
}

class BeanOne implements InitializingBean {
    public void init() {
        System.out.println("[BeanOne] init called!");
    }

    @Override
    public void afterPropertiesSet() throws Exception{
        System.out.println("[BeanOne]afterPropertiesSet init called!");
    }
}