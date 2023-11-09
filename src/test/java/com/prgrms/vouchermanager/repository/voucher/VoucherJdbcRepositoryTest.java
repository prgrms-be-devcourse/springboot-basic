package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@SpringBootTest
class VoucherJdbcRepositoryTest {
    @Autowired
    private VoucherJdbcRepository repository;
    @Autowired
    private JdbcTemplate template;

    private final Voucher voucher1 = new FixedAmountVoucher(20000);
    private final Voucher voucher2 = new PercentAmountVoucher(10);
    private final static String DELETE_VOUCHERS_QUERY = "delete from vouchers;";

    @BeforeEach
    void beforeEach() {
        repository.create(voucher2);
    }

    @AfterEach
    void afterEach() {
        template.execute(DELETE_VOUCHERS_QUERY);
    }

    @Test
    @DisplayName("create")
    void create() {
        Voucher createVoucher = repository.create(voucher1);
        Assertions.assertThat(createVoucher).isSameAs(voucher1);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Voucher> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Voucher voucher = repository.create(voucher1);
        Voucher findVoucher = repository.findById(voucher.getId());

        Assertions.assertThat(findVoucher.getDiscount()).isEqualTo(voucher.getDiscount());
        Assertions.assertThat(findVoucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("updateDiscount")
    void updateDiscount() {
        Voucher voucher = new PercentAmountVoucher(voucher2.getId(), 20);
        Voucher updateVoucher = repository.updateDiscount(voucher);
        Assertions.assertThat(updateVoucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        Assertions.assertThat(repository.delete(voucher2.getId())).isEqualTo(1);
    }
}
