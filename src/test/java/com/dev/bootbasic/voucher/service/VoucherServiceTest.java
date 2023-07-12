package com.dev.bootbasic.voucher.service;

import com.dev.bootbasic.voucher.domain.FixedAmountVoucher;
import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherFactory;
import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import com.dev.bootbasic.voucher.dto.VoucherDetailsResponse;
import com.dev.bootbasic.voucher.repository.InMemoryVoucherRepository;
import com.dev.bootbasic.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static com.dev.bootbasic.voucher.domain.VoucherType.FIXED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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

        assertThat(voucherId).isNotNull();
    }

    @DisplayName("바우처를 id값을 통해 찾을수 있다.")
    @Test
    void findTest() {
        UUID voucherId = UUID.randomUUID();
        int discountAmount = 5000;
        Voucher voucher = FixedAmountVoucher.of(voucherId, discountAmount);
        voucherRepository.saveVoucher(voucher);

        VoucherDetailsResponse foundVoucher = voucherService.findVoucher(voucherId);

        UUID foundId = foundVoucher.id();
        assertThat(foundId).isEqualTo(voucherId);
    }

    @DisplayName("조회할 수 없는 바우처ID를 조회시 예외를 발생시킨다.")
    @Test
    void findFailTest() {
        assertThatThrownBy(() -> voucherService.findVoucher(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
