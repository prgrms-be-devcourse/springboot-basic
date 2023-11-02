package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.AppConfig;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

@SpringJUnitConfig
class VoucherControllerTest {

    @Autowired
    private VoucherJdbcRepository repository;
    @Autowired
    private VoucherService service;
    private VoucherController controller;
    @Autowired
    private JdbcTemplate template;

    private final Voucher voucher = new PercentAmountVoucher(10);

    @Configuration
    static class TestConfig extends AppConfig {
    }

    @BeforeEach
    void beforeEach() {
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
        Assertions.assertThat(list.size()).isEqualTo(1);
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
