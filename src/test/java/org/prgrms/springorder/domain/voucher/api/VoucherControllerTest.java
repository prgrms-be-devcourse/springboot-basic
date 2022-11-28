package org.prgrms.springorder.domain.voucher.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.common.ControllerIntegrationBase;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.api.request.VoucherIdRequest;
import org.prgrms.springorder.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.repository.VoucherJdbcRepository;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("VoucherController 통합 테스트")
@Transactional
@SpringBootTest
@ActiveProfiles("dev")
class VoucherControllerTest extends ControllerIntegrationBase {

    @Autowired
    private VoucherController voucherController;

    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    @Autowired
    private CustomerJdbcRepository customerJdbcRepository;

    @DisplayName("create 테스트 - 바우처가 생성된다")
    @Test
    void createVoucherSuccessTest() {
        //given
        long amount = 100L;
        VoucherType fixedVoucherType = VoucherType.FIXED;

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(fixedVoucherType,
            amount);

        //when
        Response response = voucherController.createVoucher(voucherCreateRequest);
        //then
        assertThat(response.getResponse()).contains("id", fixedVoucherType.name(),
            String.valueOf(amount));
    }

    @DisplayName("findAll 테스트 - 저장된 모든 바우처가 조회된다")
    @Test
    void findAllVoucherSuccessTest() {
        //given
        int saveCount = 5;
        List<Voucher> vouchers = createVouchers(saveCount);

        String vouchersString = vouchers.stream().map(Objects::toString).collect(Collectors.joining("\n"));
        //when

        Response response = voucherController.findAllVoucher();
        //then
        assertThat(response.getResponse()).contains(vouchersString);
    }

    @DisplayName("findAll 테스트 - 저장된 바우처가 없으면 예외를 던진다.")
    @Test
    void findAllVoucherFailReturnEmptyThrowsExceptionTest() {
        //given & when

        assertThrows(EntityNotFoundException.class, () ->
                voucherController.findAllVoucher()
            );

        //then
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertTrue(vouchers.isEmpty());
    }

    @DisplayName("findCustomerWithVoucher 테스트 - 저장된 바우처가 없으면 예외를 던진다.")
    @Test
    void findCustomerWithVoucherFailReturnEmptyThrowsExceptionTest() {
        //given & when
        VoucherIdRequest voucherIdRequest = new VoucherIdRequest(UUID.randomUUID().toString());

        assertThrows(EntityNotFoundException.class, () ->
            voucherController.findCustomerWithVoucher(voucherIdRequest)
        );

        //then
        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        assertTrue(vouchers.isEmpty());
    }

    private List<Voucher> createVouchers(int saveCount) {
        return IntStream.range(0, saveCount)
            .mapToObj(value -> {
                FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),
                    100L);
                return voucherJdbcRepository.insert(fixedAmountVoucher);
            }).collect(Collectors.toList());
    }

}