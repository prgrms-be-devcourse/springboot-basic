package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DbVoucherRepositoryTest {

    @Autowired
    VoucherRepository repository;

    @Test
    @DisplayName("insert 시 DB에 바우처가 성공적으로 저장되어야 한다.")
    void DB_바우처_저장_테스트() {
        Voucher voucher = VoucherFactory.createVoucher(FixedAmount, 5000L);
        assertDoesNotThrow(() -> repository.registerVoucher(voucher));
    }


    @Test
    @DisplayName("바우처 ID로 조회 시 바우처가 성공적으로 조회되어야 한다.")
    void DB_바우처_단건조회_테스트() {
        Voucher voucher = VoucherFactory.createVoucher(FixedAmount, 5000L);
        repository.registerVoucher(voucher);
        Optional<Voucher> result = repository.findById(voucher.getVoucherId());
        assertTrue(result.isPresent());

        Voucher findOne = result.get();

        assertEquals(voucher, findOne);
    }

    @Test
    @DisplayName("전체 조회 시 모든 바우처가 성공적으로 조회되어야 한다.")
    void DB_바우처_전체조회_테스트() {
        Voucher voucher1 = VoucherFactory.createVoucher(FixedAmount, 5000L);
        repository.registerVoucher(voucher1);

        List<Voucher> allVouchers = repository.findAllVouchers();

        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers, hasSize(1));
        assertThat(allVouchers, everyItem(samePropertyValuesAs(voucher1)));
    }
}