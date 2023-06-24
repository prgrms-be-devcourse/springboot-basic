package com.dev.bootbasic.voucher.service;

import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.repository.InMemoryVoucherRepository;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;


class VoucherServiceTest {

    private VoucherService voucherService;
    private VoucherRepository voucherRepository;

    @BeforeEach
    public void setUp() {
        voucherRepository = new InMemoryVoucherRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @DisplayName("바우처 유형과 금액으로 바우처를 생성할 수 있고 생성된 바우처의 id를 반환한다.")
    @ParameterizedTest
    @CsvSource({"fixed, 5000", "percent, 50"})
    void createVoucherTest(String type, int amount) {
        VoucherCreateRequest request = new VoucherCreateRequest(type, amount);

        UUID voucherId = voucherService.createVoucher(request);

        Assertions.assertThat(voucherId).isNotNull();
    }

}
