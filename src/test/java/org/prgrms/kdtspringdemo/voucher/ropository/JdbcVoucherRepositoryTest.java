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
import java.util.NoSuchElementException;
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
        Voucher result = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(result.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    void 바우처_ID_조회_실패_테스트() {
        //given
        UUID voucherId = UUID.randomUUID();

        //when & then
        assertThatThrownBy(() -> voucherRepository.findById(voucherId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("[ERROR] : 조회된 바우처 ID가 없습니다.");
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
        Voucher response = voucherRepository.update(updateVoucher);

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

        //when
        Voucher deletedVoucher = voucherRepository.deleteById(voucher.getVoucherId());

        //then
        assertThat(deletedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    void 바우처_삭제_실패_테스트() {
        //given
        Voucher voucher = new FixedAmountVoucher(1000);

        //when & then
        assertThatThrownBy(() -> voucherRepository.deleteById(voucher.getVoucherId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("[ERROR] : 조회된 바우처 ID가 없습니다.");
    }
}
