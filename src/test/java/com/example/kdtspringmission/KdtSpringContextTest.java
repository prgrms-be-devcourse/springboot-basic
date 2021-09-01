package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// @ExtendWith(SpringExtension.class)
// @ContextConfiguration  // 별도의 설정정보를 넘겨주지 않으면 내부에 설정 정보가 있는지 찾아봄
@SpringJUnitConfig
class KdtSpringContextTest {

    @Configuration
    @ComponentScan(
        basePackageClasses = {AppConfig.class}
    )
    static class Config {
    }

    @Autowired
    ApplicationContext ac;

    @Test
    @DisplayName("applicationContext가 생성되어야 함")
    void testApplicationContext() {
        assertThat(ac).isNotNull();
    }

    @Test
    @DisplayName("VoucherRepository가 생성되어 있어야 한다.")
    void testVoucherRepositoryCreation() {
        VoucherRepository bean = ac.getBean(VoucherRepository.class);
        assertThat(bean).isInstanceOf(VoucherRepository.class);

    }
}
