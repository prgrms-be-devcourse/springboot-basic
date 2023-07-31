package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.stream.voucher.JdbcVoucherStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class JdbcVoucherStreamTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    VoucherStream voucherStream;

    @Test
    @DisplayName("바우처를 저장하면 db에 저장되어야 함.")
    void save() {
        // given
        assertThat(voucherStream.findAll()).isEmpty();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("skdodoll", 10000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher("testId", 80);

        // when
        voucherStream.save(fixedAmountVoucher);
        voucherStream.save(percentDiscountVoucher);
        // then
        var resultSize = voucherStream.findAll().size();
        assertThat(resultSize).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처 조회 로직 검증")
    void findAll() {

        // given
        String voucherId = "skdodoll";
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10000);
        voucherStream.save(fixedAmountVoucher);
        // when
        Map<String, Voucher> voucherMap = voucherStream.findAll();
        // then
        Set<String> keySet = voucherMap.keySet();
        assertThat(keySet).contains(voucherId);

        Voucher findVoucher = voucherMap.get(voucherId);
        String actualId = findVoucher.getVoucherId();

        assertAll(
                () -> assertThat(actualId).isSameAs("skdodoll"),
                () -> assertThat(findVoucher).isInstanceOf(FixedAmountVoucher.class)
        );

    }

    @Test
    @DisplayName("바우처 단건 조회 로직 검증")
    void findById() {
        // given
        String voucherId = "skdodoll";
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10000);
        voucherStream.save(fixedAmountVoucher);

        // when
        Voucher findVoucher = ((JdbcVoucherStream) voucherStream).findById(voucherId);

        // then
        var actualId = findVoucher.getVoucherId();
        assertThat(actualId).isEqualTo("skdodoll");
    }

    @Test
    @DisplayName("업데이트 로직 검증")
    void update() {

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("test-user", 10000);
        voucherStream.save(fixedAmountVoucher);
        // when
        fixedAmountVoucher.setAmount(20000);
        ((JdbcVoucherStream) voucherStream).update(fixedAmountVoucher);
        // then
        Map<String, Voucher> voucherMap = voucherStream.findAll();
        Voucher findVoucher = voucherMap.get(fixedAmountVoucher.getVoucherId());
        var actualAmount = ((FixedAmountVoucher) findVoucher).getAmount();

        assertThat(actualAmount).isEqualTo(20000);

    }

    @Test
    @DisplayName("삭제 로직 검증")
    void deleteAll() {

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("test123", 10000);
        voucherStream.save(fixedAmountVoucher);
        assertThat(voucherStream.findAll().size()).isEqualTo(1);
        // when
        ((JdbcVoucherStream) voucherStream).deleteAll();

        // then
        int resultSize = voucherStream.findAll().size();
        assertThat(resultSize).isEqualTo(0);
    }
}
