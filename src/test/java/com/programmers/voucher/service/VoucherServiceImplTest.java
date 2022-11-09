package com.programmers.voucher.service;

import com.programmers.voucher.repository.VoucherRepository;
import com.programmers.voucher.voucher.FixedAmountVoucher;
import com.programmers.voucher.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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
    void 바우처저장테스트() {
        String userInputType = "F";
        String userInputValue = "5000";

        Voucher voucher = service.register(userInputType, userInputValue);

        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher.getValue()).isEqualTo(Long.parseLong(userInputValue));
    }

    //    @Test
//    @DisplayName("저장후 리턴하는 바우처의 Id, value값은 저장하기 전 바우처의 Id, value와 동일해야 한다.")
//    void 바우처저장() {
//
//        Voucher voucher = FixedAmount.createVoucher(3000);
//        Voucher result = service.register(FixedAmount.getType().get(0), "5000");
//
//        assertThat(result.getVoucherId()).isEqualTo(voucher.getVoucherId());
//        assertThat(result.getValue()).isEqualTo(voucher.getValue());
//    }
//
    @Test
    @DisplayName("저장한 바우처의 Id로 조회할 경우, 저장한 바우처와 조회한 바우처의 Equals 결과는 false")
    void 바우처단건조회() {
        Voucher voucher = PercentDiscount.createVoucher(5);
        repository.registerVoucher(voucher);

        Voucher findOne = service.getVoucher(voucher.getVoucherId());
        assertEquals(voucher, findOne);

    }

    @Test
    @DisplayName("저장한 바우처의 Id와 다른 Id로 조회할 경우, 저장한 바우처와 조회한 바우처의 Equals 결과는 false")
    void 바우처단건조회2() {
        Voucher original = PercentDiscount.createVoucher(5);
        repository.registerVoucher(original);

        Voucher another = FixedAmount.createVoucher(3000);
        repository.registerVoucher(another);

        Optional<Voucher> findOne = repository.findById(another.getVoucherId());
        assertTrue(findOne.isPresent());

        assertNotEquals(original, another);
        assertNotEquals(original, findOne.get());
    }

    @Test
    void 모든바우처조회() {
        List<Voucher> vouchers = service.findAll();
        assertThat(vouchers.size()).isEqualTo(0);

        Voucher voucher1 = FixedAmount.createVoucher(3000);
        Voucher voucher2 = PercentDiscount.createVoucher(7);

        repository.registerVoucher(voucher1);
        repository.registerVoucher(voucher2);

        vouchers = service.findAll();

        assertThat(vouchers.size()).isEqualTo(2);
        assertThat(vouchers).contains(voucher1, voucher2);
    }

    @Test
    @DisplayName("없는 아이디로 바우처를 조회할 경우 런타임 예외를 던져야 한다.")
    void 바우처조회실패() {
        UUID notExistId = UUID.randomUUID();

        assertThrowsExactly(RuntimeException.class,
                () -> service.getVoucher(notExistId));
    }
}