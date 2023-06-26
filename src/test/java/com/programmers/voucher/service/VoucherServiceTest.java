package com.programmers.voucher.service;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {

    private VoucherRepository voucherRepository;
    private VoucherService voucherService;


    @BeforeEach
    public void setUp() {
        voucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherServiceImpl(voucherRepository);
    }

    @DisplayName("바우처 타입과 할인값으로 바우처를 생성하고 바우처를 반환")
    @ParameterizedTest
    @CsvSource(value = {
            "2, 10"
            , "2, 50"
            , "1, 50"
            , "2, 100"
            , "1, 999999"
            , "1, 30"
    })
    void createVoucherTest(String command, long value) {
        Discount discount = Discount.of(VoucherType.of(command), value);

        VoucherRequestDto requestDto = new VoucherRequestDto(discount);

        Voucher voucher = voucherService.create(requestDto);

        assertThat(voucher).isNotNull();
    }
}
