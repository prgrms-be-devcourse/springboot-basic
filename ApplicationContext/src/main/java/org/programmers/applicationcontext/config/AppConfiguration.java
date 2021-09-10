package org.programmers.applicationcontext.config;
import org.programmers.applicationcontext.voucher.Voucher;
import org.programmers.applicationcontext.voucher.VoucherRepository;
import org.programmers.applicationcontext.voucher.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.UUID;

// OrderService, VoucherService, OrderRepository, VoucherRepository의 생성에 대한 책임을 갖고 있고, 각각의 서비스에 대한 의존성 관리를 담당한다
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

}
