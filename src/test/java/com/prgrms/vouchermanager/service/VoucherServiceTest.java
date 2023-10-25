package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.repository.voucher.VoucherFileRepository;
import com.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    private VoucherService service;

    @Mock
    private VoucherRepository repository;

    @BeforeEach
    void beforeEach() {
        service = new VoucherService(repository);
        System.out.println(repository.getClass().getName());
    }

    @Test
    @DisplayName("create")
    void createTest() {
        Voucher voucher = service.create(VoucherType.FIXED, 20000);

        Assertions.assertThat(voucher.getDiscount()).isEqualTo(20000);
        Assertions.assertThat(voucher instanceof FixedAmountVoucher).isTrue();
    }
    @Test
    @DisplayName("list")
    void listTest() {
        service.create(VoucherType.FIXED, 20000);
        service.create(VoucherType.PERCENT, 10);
        List<Voucher> list = service.list();
        Assertions.assertThat(list.size()).isEqualTo(2);

    }
}
