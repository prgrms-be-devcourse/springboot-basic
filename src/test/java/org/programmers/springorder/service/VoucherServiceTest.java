package org.programmers.springorder.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "command.line.runner.enabled=false")
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    void createVoucher() {
        // given
        VoucherRequestDto request1 = new VoucherRequestDto(1000, VoucherType.FIXED);
        VoucherRequestDto request2 = new VoucherRequestDto(10, VoucherType.PERCENT);

        // when
        voucherService.createVoucher(request1);
        voucherService.createVoucher(request2);

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @DisplayName("전체 바우처 조회에 성공한다.")
    void getAllVoucher() {
        // given
        Voucher voucher1 = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        Voucher voucher2 = Voucher.toVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT);

        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        // when
        List<VoucherResponseDto> voucherList = voucherService.getAllVoucher();

        // then
        assertThat(voucherList).hasSize(2);
    }

    @Test
    @DisplayName("바우처 ID가 존재하지 않으면, 에러 메시지를 띄운다.")
    void findByIdFail() {
        // given
        UUID findVoucherId = UUID.randomUUID();

        // then
        assertThatThrownBy(() -> voucherService.findById(findVoucherId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력한 바우처 ID가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("바우처 수정에 성공한다.")
    void updateVoucher() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        VoucherRequestDto updatedRequestDto = new VoucherRequestDto(10, VoucherType.PERCENT);

        // when
        voucherService.updateVoucher(voucherId, updatedRequestDto);
        Optional<Voucher> updatedVoucher = voucherRepository.findById(voucherId);

        // then
        assertThat(updatedVoucher).isPresent();
        assertThat(updatedVoucher.get().getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(updatedVoucher.get().getDiscountValue()).isEqualTo(10);
    }

    @Test
    @DisplayName("바우처 삭제에 성공한다.")
    void deleteVoucher() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);
        voucherRepository.insert(voucher);

        // when
        voucherService.deleteVoucher(voucherId);
        Optional<Voucher> findVoucher = voucherRepository.findById(voucherId);

        // then
        assertThat(findVoucher).isNotPresent();
    }
}