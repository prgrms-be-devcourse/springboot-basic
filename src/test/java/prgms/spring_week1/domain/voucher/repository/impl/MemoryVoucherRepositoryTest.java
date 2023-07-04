package prgms.spring_week1.domain.voucher.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.impl.FixedAmountVoucher;
import prgms.spring_week1.domain.voucher.model.impl.PercentDiscountVoucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setUp(){
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED,10000));
        voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT,30));
    }

    @Test
    void insert() {
        //given
        List<Voucher> voucherList = voucherRepository.findAll();
        //when
        int voucherListSize = voucherList.size();
        //then
        assertEquals(2,voucherListSize);
    }

    @Test
    void findAll() {
        //given
        Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED,10000);
        //when
        voucherRepository.insert(newVoucher);
        //then
        assertEquals(3,voucherRepository.findAll().size());
    }
}
