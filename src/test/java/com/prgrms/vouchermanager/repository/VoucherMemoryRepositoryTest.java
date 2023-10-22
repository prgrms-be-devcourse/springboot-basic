package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
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
        repository.create(voucher);

        List<Voucher> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.get(0)).isSameAs(voucher);
    }

    @Test
    @DisplayName("바우처 목록")
    void listTest() {
        List<Voucher> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(0);
    }

}
