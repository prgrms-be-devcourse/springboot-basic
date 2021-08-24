package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


// Junit과 상호작용하면서 ApplicationContext 만들어지게 하기 위해서 SPRING Extension을 사용해야합니다.
@ExtendWith(SpringExtension.class) // 실제 test Context가 상호작용하도록 해줌.
@ContextConfiguration // (classes = {AppConfiguration.class}) // test 할땐 별도의 ApplicationConfiguration Class를 만들어줘서 전달해 줄 수 있습니다.
public class KdtSpringContextTest {

    // 위의 @ContextConfiguration 별도로 Bean 설정을 정의한 클래스나 xml을 전달하지 않으면,
    // 기본적으로 KdtSpringContextTest 클래스 안에 있는 static class에 정의된 @Configuration이 달린 걸 찾게 됩니다.
    @Configuration
    static class Config {
        @Bean
        VoucherRepository voucherRepository() {
            return new VoucherRepository() {
                @Override
                public Optional<Voucher> findById(UUID voucherId) {
                    return Optional.empty();
                }

                @Override
                public List<Voucher> find() {
                    return null;
                }

                @Override
                public Voucher insert(Voucher voucher) {
                    return null;
                }
            };
        }
    }

    @Autowired
    ApplicationContext context;

    @Test
    @DisplayName("applicationContext가 생성되어야 한다.")
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    public void testVoucherRepositoryCreation() {
        var bean = context.getBean(VoucherRepository.class);
        assertThat(bean, notNullValue());
    }
}
