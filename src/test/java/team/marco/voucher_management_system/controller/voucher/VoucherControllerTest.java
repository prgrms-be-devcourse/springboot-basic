package team.marco.voucher_management_system.controller.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherCreateRequest;
import team.marco.voucher_management_system.controller.voucher.dto.VoucherResponse;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.service.voucher.VoucherCreateServiceRequest;
import team.marco.voucher_management_system.service.voucher.VoucherService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;

@WebMvcTest(controllers = VoucherController.class)
class VoucherControllerTest {
    @MockBean
    private VoucherService voucherService;
    private VoucherController voucherController;

    @BeforeEach
    void setup() {
        this.voucherController = new VoucherController(voucherService);
    }

    @DisplayName("쿠폰을 생성할 수 있다.")
    @Test
    void createVoucher() {
        // given
        VoucherCreateRequest request = new VoucherCreateRequest(FIXED, 1_000);
        VoucherCreateServiceRequest serviceRequest = request.toServiceRequest();
        Voucher voucher = new Voucher.Builder(1L, request.getVoucherType(), request.getDiscountValue()).build();

        when(voucherService.createVoucher(serviceRequest)).thenReturn(voucher);

        // when
        voucherController.createVoucher(request);

        // then
        assertThatNoException();
    }

    @DisplayName("discountValue가 0이하인 쿠폰 생성 요청은 할 수 없다.")
    @Test
    void createVoucherWithZeroDiscountValue() {
        // given
        int zero = 0;

        // when then
        assertThatThrownBy(() -> voucherController.createVoucher(
                        new VoucherCreateRequest(FIXED, zero)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액 또는 할인율은 양수입니다.");
    }

    @DisplayName("쿠폰 ID로 쿠폰을 조회할 수 있다.")
    @Test
    void getVoucher() {
        // given
        Voucher voucher = new Voucher.Builder(1L, FIXED, 1_000).build();

        when(voucherService.getVoucher(voucher.getId())).thenReturn(voucher);

        // when
        VoucherResponse response = voucherController.getVoucher("1");

        // then
        assertThat(response)
                .extracting("id", "code", "name")
                .containsExactly(1L, voucher.getCode(), voucher.getName());
    }

    @DisplayName("전체 쿠폰 목록을 조회할 수 있다.")
    @Test
    void getVouchers() {
        // given
        List<Voucher> vouchers = new ArrayList<>();

        when(voucherService.getVouchers()).thenReturn(vouchers);

        // when
        List<VoucherResponse> response = voucherController.getVouchers();

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("쿠폰 ID로 쿠폰을 삭제할 수 있다.")
    @Test
    void deleteVoucher() {
        // when
        voucherController.deleteVoucher("1");

        // then
        assertThatNoException();
    }
}