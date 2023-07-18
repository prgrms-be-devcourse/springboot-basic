package org.prgrms.kdtspringdemo.voucher.ropository;

import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.model.entity.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.PercentAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@ContextConfiguration(classes = JdbcVoucherRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @Test
    void 바우처_저장_성공_테스트() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000);

        //when
        Voucher savedVoucher = voucherRepository.save(voucher);

        //then
        assertThat(savedVoucher).isEqualTo(voucher);
    }

    @Test
    void 바우처_ID_조회_성공_테스트() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000);
        voucherRepository.save(voucher);

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        Voucher result = foundVoucher.get();

        //then
        assertThat(result.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    void 바우처_ID_조회_실패_테스트() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        Optional<Voucher> result = voucherRepository.findById(voucherId);

        //then
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    void 바우처_모두_조회_성공_테스트() {
        //given
        List<Voucher> beforeVoucherList = voucherRepository.findAll();
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(1000);
        PercentAmountVoucher voucher2 = new PercentAmountVoucher(10);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        //when
        List<Voucher> afterVoucherList = voucherRepository.findAll();

        //then
        assertThat(afterVoucherList.size() - beforeVoucherList.size()).isEqualTo(2);
    }

    @Test
    void 바우처_수정_성공_테스트() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000);
        Voucher savedVoucher = voucherRepository.save(voucher);
        PercentAmountVoucher updateVoucher = new PercentAmountVoucher(savedVoucher.getVoucherId(), 10);

        //when
        voucherRepository.update(updateVoucher.getVoucherId(), updateVoucher.getVoucherType(), updateVoucher.getAmount());
        Voucher response = voucherRepository.findById(updateVoucher.getVoucherId()).get();

        //then
        assertThat(response.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(response.getVoucherId()).isEqualTo(updateVoucher.getVoucherId());
        assertThat(response.getVoucherType()).isEqualTo(updateVoucher.getVoucherType());
        assertThat(response.getAmount()).isEqualTo(updateVoucher.getAmount());
    }

    @Test
    void 바우처_삭제_성공_테스트() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000);
        voucherRepository.save(voucher);
        int beforeSize = voucherRepository.findAll().size();

        //when
        voucherRepository.deleteById(voucher.getVoucherId());
        int afterSize = voucherRepository.findAll().size();

        //then
        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }
}
