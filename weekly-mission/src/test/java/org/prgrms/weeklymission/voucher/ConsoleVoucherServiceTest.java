package org.prgrms.weeklymission.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.weeklymission.voucher.domain.FixedAmountVoucher;
import org.prgrms.weeklymission.voucher.domain.PercentDiscountVoucher;
import org.prgrms.weeklymission.voucher.domain.Voucher;
import org.prgrms.weeklymission.voucher.repository.VoucherRepository;
import org.prgrms.weeklymission.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.weeklymission.utils.ErrorMessage.NO_VOUCHER;
import static org.prgrms.weeklymission.utils.ErrorMessage.OPTION_ERROR;

@SpringBootTest
@ActiveProfiles("local")
class ConsoleVoucherServiceTest {
    @Autowired
    private VoucherService service;
    @Autowired
    private VoucherRepository repository;

    private final String discount = "10";
    private String option;

    @BeforeEach
    public void beforeEach() {
        repository.clear();
    }

    @Test
    @DisplayName("옵션 1을 선택하였을 경우")
    void test_option_1() {
        option = "1";

        Voucher voucher = service.createVoucher(option, discount);

        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("옵션 2를 선택하였을 경우")
    void test_option_2() {
        option = "2";
        Voucher voucher = service.createVoucher(option, discount);
        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @Test
    @DisplayName("1, 2를 제외한 다른 옵션을 선택하였을 경우")
    void test_option_others() {
        option = "3";
        assertThrows(RuntimeException.class, () -> service.createVoucher(option, discount));

        option = "abc";
        String message = assertThrows(RuntimeException.class, () -> service.createVoucher(option, discount)).getMessage();

        assertThat(message).isEqualTo(OPTION_ERROR.getMessage());
    }

    @Test
    @DisplayName("Discount 입력값이 숫자타입이 아니라면 파싱이 되지 않을 경우")
    void test_parsing_exception() {
        option = "1";
        assertThrows(NumberFormatException.class, () -> service.createVoucher(option, "abc"));
    }

    @Test
    @DisplayName("바우처 생성 후 조회 시 예외 없이 조회가 잘 되어야 한다.")
    void test_search() {
        option = "1";
        service.createVoucher(option, discount);
        service.createVoucher(option, discount);
        service.createVoucher(option, discount);

        List<Voucher> vouchers = service.searchAllVouchers();

        assertThat(vouchers).isNotNull();
        assertThat(vouchers).doesNotContainNull();
        assertThat(vouchers.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("바우처를 생성하지 않고 조회 시 예외가 발생해야 한다.")
    void test_search_exception() {
        String message = assertThrows(RuntimeException.class, () -> service.searchAllVouchers()).getMessage();

        assertThat(message).isEqualTo(NO_VOUCHER.getMessage());
    }

    @Test
    @DisplayName("바우처 아이디로 찾기")
    void test_voucher_find() {
        option = "2";
        Voucher voucher = service.createVoucher(option, discount);

        Voucher findVoucher = service.findVoucherById(voucher.getVoucherId());

        assertThat(findVoucher).isNotNull();
        assertThat(findVoucher.getClass()).isEqualTo(PercentDiscountVoucher.class);
    }

    @Test
    @DisplayName("바우처 아이디로 바우처를 찾지 못할 경우")
    void test_voucher_find_exception() {
        assertThrows(RuntimeException.class, () -> service.findVoucherById(UUID.randomUUID()));
    }
}