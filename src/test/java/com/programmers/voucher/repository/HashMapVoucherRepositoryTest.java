package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashMapVoucherRepositoryTest {

    @Autowired
    VoucherRepository repository;

    @BeforeEach
    void clear() {
        repository.deleteAll();
    }

    @Test
    void 맵에_바우처_저장() {
        VoucherType type = FixedAmount;

        Voucher voucher = VoucherFactory.createVoucher(type, 3000);
        Voucher findOne = repository.registerVoucher(voucher);

        assertEquals(voucher, findOne);
    }

    @Test
    void 맵에서_Id로_바우처_조회() {
        VoucherType type = PercentDiscount;
        Voucher voucher = VoucherFactory.createVoucher(type, 5000);
        repository.registerVoucher(voucher);

        Optional<Voucher> findOne = repository.findById(voucher.getVoucherId());
        assertTrue(findOne.isPresent());

        assertSame(findOne.get(), voucher);
    }

    @Test
    void 맵의_모든_바우처_조회() {

        VoucherType typeP = PercentDiscount;
        Voucher voucher1 = VoucherFactory.createVoucher(typeP, 5000);
        repository.registerVoucher(voucher1);

        VoucherType typeF = FixedAmount;
        Voucher voucher2 = VoucherFactory.createVoucher(typeF, 5000);
        repository.registerVoucher(voucher2);

        List<Voucher> result = repository.findAllVouchers();

        assertEquals(2, result.size());
    }

}