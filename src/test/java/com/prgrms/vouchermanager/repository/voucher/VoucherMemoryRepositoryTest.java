package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class VoucherMemoryRepositoryTest {

    private VoucherMemoryRepository repository;

    private final Voucher voucher1 = new FixedAmountVoucher(20000);
    private final Voucher voucher2 = new PercentAmountVoucher(10);

    @BeforeEach
    void setup() {
        repository = new VoucherMemoryRepository();
        repository.create(voucher2);
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
        repository.delete(voucher2.getId());
        Assertions.assertThat(repository.findAll().size()).isEqualTo(0);
    }

}
