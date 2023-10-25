package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.repository.voucher.VoucherMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class VoucherMemoryRepositoryTest {

    private VoucherMemoryRepository repository;

    @BeforeEach
    void setup() {
        repository = new VoucherMemoryRepository();
    }
    @Test
    @DisplayName("바우처 생성")
    void createTest() {
        PercentAmountVoucher voucher = new PercentAmountVoucher(20);
        Voucher createVoucher = repository.create(voucher);

        Assertions.assertThat(createVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 목록")
    void listTest() {
        List<Voucher> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(0);
    }

}
