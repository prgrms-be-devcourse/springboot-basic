package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@Profile("local")
class FileVoucherRepositoryTest {

    static FixedAmountVoucher fixedAmountVoucher;

    static VoucherRepository voucherRepository;

    @BeforeAll
    static void setUp(){
        voucherRepository = new FileVoucherRepository();
        fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    }

    @Test
    @DisplayName("File Voucher 를 저장할 때 (postConstructor 가 동작하지 않으면) 메모리로 임시 관리한다.")
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

        assertThat(retrievedVoucher, is(not(sameInstance(fixedAmountVoucher))));
        assertThat(voucher.getClass(), is(sameInstance(fixedAmountVoucher.getClass())));
    }

}