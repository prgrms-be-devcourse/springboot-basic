package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.AppConfig;
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

    @Autowired
    private VoucherJdbcRepository repository;
    @Autowired
    private VoucherService service;
    @Autowired
    private JdbcTemplate template;
    private final Voucher voucher = new PercentAmountVoucher(10);
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    @Configuration
    static class TestConfig extends AppConfig {
    }

    @BeforeEach
    void beforeEach() {
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
        List<Voucher> list = service.findAll();
        Assertions.assertThat(list.size()).isEqualTo(1);
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
