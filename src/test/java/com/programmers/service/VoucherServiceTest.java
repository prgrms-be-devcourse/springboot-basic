package com.programmers.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.programmers.domain.FixedAmountVoucher;
import com.programmers.domain.Voucher;
import com.programmers.repository.MemoryVoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

@ActiveProfiles("local")
class VoucherServiceTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;

    @Mock
    MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    public void before() {
        System.setOut(new PrintStream(outputStream));

        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void after() {
        System.setOut(printStream);
    }

    @DisplayName("바우처 이름을 변형한다")
    @CsvSource(value = {
            "Fixed Amount Voucher : fixedamountvoucher",
            "Percent Discount Voucher : percentdiscountvoucher"
    }, delimiter = ':')
    @ParameterizedTest
    void reformatVoucherName(String input, String expected) {
        //given
        VoucherService voucherService = new VoucherService(memoryVoucherRepository);

        //when
        String result = voucherService.reformatVoucherType(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("바우처를 저장하고 조회한다")
    @Test
    void saveAndFindAll() {
        //given
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(uuid, "voucherName1", 10L);
        List<Voucher> vouchers = List.of(fixedAmountVoucher);

        doNothing().when(memoryVoucherRepository).save(fixedAmountVoucher);
        when(memoryVoucherRepository.findAll()).thenReturn(vouchers);

        VoucherService voucherService = new VoucherService(memoryVoucherRepository);

        //when
        voucherService.save(fixedAmountVoucher);
        List<Voucher> result = voucherService.findAll();

        //then
        assertThat(result.get(0).getVoucherId()).isEqualTo(uuid);
    }

    @DisplayName("주어진 입력을 Long 타입으로 바꾸어 리턴한다")
    @Test
    void changeDiscountValueToNumber() {
        //given
        VoucherService voucherService = new VoucherService(memoryVoucherRepository);

        String input = "123";

        //when
        Long result = voucherService.changeDiscountValueToNumber(input);

        //then
        Assertions.assertThat(result).isEqualTo(123L);
    }

    @DisplayName("주어진 입력이 숫자가 아니면 예외처리한다")
    @Test
    void changeNumberWithWrongInput() {
        //given
        VoucherService voucherService = new VoucherService(memoryVoucherRepository);

        //when
        //then
        Assertions.assertThatThrownBy(() -> voucherService.changeDiscountValueToNumber("abc"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}