package org.prgrms.kdt.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.dao.entity.voucher.FixedAmountVoucher;
import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherFactory;
import org.prgrms.kdt.dao.repository.voucher.FileVoucherRepository;
import org.prgrms.kdt.dao.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest(classes = {FileVoucherRepository.class, VoucherFactory.class})
class FileVoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("저장된 바우처를 voucherId를 이용해 찾는 기능 검증")
    void voucherId_이용한_바우처찾기() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        // when
        voucherRepository.insert(voucher);
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(findVoucher.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처가 제대로 들어갔는지 repository 내부 바우처의 갯수로 검증")
    void insert() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        // when
        voucherRepository.insert(voucher);
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();
        System.out.println(getVoucherCount);

        // then
        assertThat(getVoucherCount, is(1));
    }

    @Test
    @DisplayName("저장된 바우처들이 제대로 나오는지 바우처 갯수를 이용한 검증")
    void getAllStoredVoucher() {
        // given
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 1000L));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 2000L));
        voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 3000L));

        // when
        int getVoucherCount = voucherRepository.getAllStoredVoucher().size();

        // then
        assertThat(getVoucherCount, is(3));
    }

}