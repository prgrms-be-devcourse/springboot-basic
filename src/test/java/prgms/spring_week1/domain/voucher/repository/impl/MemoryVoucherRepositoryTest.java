package prgms.spring_week1.domain.voucher.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@ExtendWith(MockitoExtension.class)
class MemoryVoucherRepositoryTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.insert(new Voucher(VoucherType.FIXED, 10000));
        voucherRepository.insert(new Voucher(VoucherType.PERCENT, 30));
    }

    @Test
    @DisplayName("바우처 리스트를 조회 했을 때 올바른 사이즈 값이 나오는 지 확인")
    void findAll() {
        //given
        List<Voucher> voucherList = voucherRepository.findAll();
        //when
        int voucherListSize = voucherList.size();
        //then
        assertThat(2, is(voucherListSize));
    }

    @Test
    @DisplayName("바우처 추가가 제대로 동작하는 지 확인")
    void insert() {
        //given
        Voucher newVoucher = new Voucher(VoucherType.FIXED, 10000);
        //when
        voucherRepository.insert(newVoucher);
        //then
        assertThat(3, is(voucherRepository.findAll().size()));
    }
}
