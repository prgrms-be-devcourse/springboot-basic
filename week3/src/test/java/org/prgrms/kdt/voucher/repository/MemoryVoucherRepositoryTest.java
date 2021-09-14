package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@Profile("dev")
@ComponentScan(basePackages = {"org.prgrms.kdt.voucher"})
class MemoryVoucherRepositoryTest {

    static FixedAmountVoucher fixedAmountVoucher;

    static VoucherRepository voucherRepository;

    @BeforeAll
    static void setUp() {
        voucherRepository = new MemoryVoucherRepository();
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED_AMOUNT_VOUCHER, LocalDateTime.now());
    }

    @Test
    @DisplayName("메모리 고정 바우처 테스트")
    void testSetFixedAmountVoucherInsert() throws IOException {
        //given
        voucherRepository.insert(fixedAmountVoucher);

        //when
        var retrievedVouchers = voucherRepository.findAll();

        //then (임시관리되었기 때문에 file 에서 정보를 읽어오지 않는다.)
        assertThat(retrievedVouchers.size(), is(not(0)));
        assertThat(retrievedVouchers.size(), is(1));
    }

    @Test
    @DisplayName("file voucher에서 아이디로 찾는 값이 없으면 Optional 빈 객체를 내보낸다. (따라서 NotNull)")
    void testFindByIdNullCheck() {
        //given
        //when
        var retrievedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var voucher = retrievedVoucher.isPresent();

        //then
        assertThat(retrievedVoucher, is(not(nullValue())));
        assertThat(voucher, is(false));
    }

    @Test
    @DisplayName("insert and findById Test")
    void testInsertAndFindById() throws IOException {
        voucherRepository.insert(fixedAmountVoucher);
        var retrievedVoucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        var voucher = retrievedVoucher.orElseThrow(NullPointerException::new);

        assertThat(retrievedVoucher.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

}