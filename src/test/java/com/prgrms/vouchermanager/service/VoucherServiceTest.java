package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

@SpringJUnitConfig
class VoucherServiceTest {

    private VoucherJdbcRepository repository;
    private VoucherService service;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private DataSource dataSource;
    private final Voucher voucher = new PercentAmountVoucher(10);
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    @Configuration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .url("jdbc:mysql://localhost:3306/voucher_manager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                    .username("root")
                    .password("suzzingV1999@")
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }

    @BeforeEach
    void beforeEach() {
        repository = new VoucherJdbcRepository(dataSource);
        service = new VoucherService(repository);

        repository.create(voucher);
    }
    @AfterEach
    void afterEach() {
        template.execute(DELETE_VOUCHERS_QUERY);
    }

    @Test
    @DisplayName("create")
    void create() {
        Voucher voucher = service.create(VoucherType.FIXED, 20000);

        Assertions.assertThat(voucher.getDiscount()).isEqualTo(20000);
        Assertions.assertThat(voucher instanceof FixedAmountVoucher).isTrue();
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Voucher> list = service.list();
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("updateDiscount")
    void updateDiscount() {
        Voucher updateVoucher = service.updateDiscount(voucher.getId(), 20);
        Assertions.assertThat(updateVoucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = repository.delete(voucher.getId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
