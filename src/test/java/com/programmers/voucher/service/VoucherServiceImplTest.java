package com.programmers.voucher.service;

import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.FixedAmountVoucher;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherFactory.createVoucher;
import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherService service;

    @Autowired
    VoucherRepository repository;

    @BeforeEach
    void clear() {
        service.deleteAll();
    }

    @Test
    @DisplayName("서비스 계층은 String으로 type, value를 받아서 Voucher를 생성 후 저장해야한다.")
    void 바우처_저장_테스트() {
        String userInputType = "F";
        String userInputValue = "5000";

        VoucherDto voucher = service.register(userInputType, userInputValue);

        assertThat(voucher.getValue()).isEqualTo(Long.parseLong(userInputValue));
    }

    @Test
    @DisplayName("저장한 바우처의 Id로 조회할 경우, 저장한 바우처와 조회한 바우처의 Equals 결과는 true")
    void 바우처_단건조회() {
        UUID id = UUID.randomUUID();
        VoucherType type = PercentDiscount;
        Voucher voucher = createVoucher(id, type, 5);

        repository.registerVoucher(voucher);
        VoucherDto findOne = service.getVoucher(id);

        assertEquals(voucher.getValue(), findOne.getValue());
        assertEquals(voucher.getType(), findOne.getType());
    }

    @Test
    void 모든_바우처_조회() {
        List<VoucherDto> vouchers = service.findAll();
        assertThat(vouchers.size()).isEqualTo(0);

        Voucher voucher1 = createVoucher(FixedAmount, 3000);
        Voucher voucher2 = createVoucher(PercentDiscount, 5);

        repository.registerVoucher(voucher1);
        repository.registerVoucher(voucher2);

        vouchers = service.findAll();

        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("없는 아이디로 바우처를 조회할 경우 런타임 예외를 던져야 한다.")
    void 바우처_조회_실패() {
        UUID notExistId = UUID.randomUUID();

        assertThrowsExactly(RuntimeException.class,
                () -> service.getVoucher(notExistId));
    }
}