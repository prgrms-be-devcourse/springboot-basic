package org.prgrms.dev.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.repository.VoucherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    static Stream<Arguments> parametersProvider() {
        return Stream.of(
                arguments("ff", UUID.randomUUID(), 1000),
                arguments("fixedd", UUID.randomUUID(), 2500),
                arguments("1000", UUID.randomUUID(), 3000)
        );
    }

    @DisplayName("잘못된 바우처 타입 명령어로 바우처를 생성할 수 없다.")
    @ParameterizedTest
    @MethodSource("parametersProvider")
    void createVoucherByInvalidVoucherTypeTest(String type, UUID voucherId, long amount) {
        assertThatThrownBy(() -> voucherService.createVoucher(type, voucherId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid voucher type input...");
    }

    @DisplayName("바우처를 생성할 수 있다.")
    @Test
    void createVoucherTest() {
        // TODO
    }

    @DisplayName("바우처의 할인정보를 수정할 수 있다.")
    @Test
    void updateDiscountValueTest() {
        // TODO
    }

    @DisplayName("바우처를 삭제할 수 있다.")
    @Test
    void deleteVoucherTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000L);

        voucherService.deleteVoucher(voucher.getVoucherId());

        verify(voucherRepository).deleteById(voucher.getVoucherId());
    }

    @DisplayName("바우처 아이디로 원하는 바우처를 조회할 수 있다.")
    @Test
    void getVoucherByIdTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 3000L);
        when(voucherRepository.findById(voucher.getVoucherId())).thenReturn(Optional.of(voucher));

        Voucher retrievedVoucher = voucherService.getVoucher(voucher.getVoucherId());

        assertThat(retrievedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        verify(voucherRepository).findById(voucher.getVoucherId());
    }

    @DisplayName("존재하지 않는 아이디로는 바우처를 조회할 수 없다.")
    @Test
    void getVoucherByNoIdTest() {
        UUID voucherId = UUID.randomUUID();

        assertThatThrownBy(() -> voucherService.getVoucher(voucherId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Can not find a voucher for " + voucherId);
    }

    @DisplayName("모든 바우처를 조회할 수 있다.")
    @Test
    void listVoucherTest() {
        doReturn(voucherList()).when(voucherRepository).findAll();

        final List<Voucher> voucherList = voucherService.listVoucher();

        assertThat(voucherList).hasSize(6);
        verify(voucherRepository).findAll();
    }

    private List<Voucher> voucherList() {
        final List<Voucher> voucherList = new ArrayList<>();
        int tmp = 1;
        for (int i = 0; i < 3; i++) {
            voucherList.add(new FixedAmountVoucher(UUID.randomUUID(), 1000L * tmp));
            voucherList.add(new PercentDiscountVoucher(UUID.randomUUID(), 10L * tmp));
            tmp += 1;
        }
        return voucherList;
    }

}