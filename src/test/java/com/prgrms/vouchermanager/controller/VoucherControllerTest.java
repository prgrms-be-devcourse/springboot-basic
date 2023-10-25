package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.repository.voucher.VoucherJdbcRepository;
import com.prgrms.vouchermanager.service.VoucherService;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@SpringJUnitConfig
class VoucherControllerTest {

    private VoucherJdbcRepository repository;
    private VoucherService service;
    private VoucherController controller;
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private DataSource dataSource;
    private final Voucher voucher = new PercentAmountVoucher(10);

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
        controller = new VoucherController(service);

        repository.create(voucher);
    }
    @AfterEach
    void afterEach() {
        template.execute("delete from vouchers;");
    }
    @Test
    @DisplayName("create")
    void create() {
        Voucher voucher = controller.create(VoucherType.FIXED, 20000);

        Assertions.assertThat(voucher.getDiscount()).isEqualTo(20000);
        Assertions.assertThat(voucher instanceof FixedAmountVoucher).isTrue();
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Voucher> list = controller.list();
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("updateDiscount")
    void updateDiscount() {
        Voucher updateVoucher = controller.updateDiscount(voucher.getId(), 20);
        Assertions.assertThat(updateVoucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        int delete = controller.delete(voucher.getId());
        Assertions.assertThat(delete).isEqualTo(1);
    }
}
