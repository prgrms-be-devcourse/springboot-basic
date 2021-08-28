package org.prgrms.kdt.devcourse.voucher;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

    @BeforeAll
    void setUp(){
        FileVoucherRepository = new FileVoucherRepository();
        newFixedVoucher = new FixedAmountVoucher(UUID.randomUUID(),100);
        newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(),10);
    }


    FileVoucherRepository FileVoucherRepository;

    FixedAmountVoucher newFixedVoucher;
    PercentDiscountVoucher newPercentVoucher;


    @Test
    @DisplayName("파일 바우처에 바우처를 추가할 수 있다.")
    @Order(1)
    void testInsert() {
        FileVoucherRepository.insert(newFixedVoucher);

        Optional<Voucher> insertedVoucher = FileVoucherRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(insertedVoucher.isEmpty(),is(false));
        assertThat(insertedVoucher.get(),samePropertyValuesAs(newFixedVoucher));
    }

    @Test
    @DisplayName("파일 바우처에서 id를 기반으로 찾을 수 있다.")
    @Order(2)
    void testFindById() {
        Optional<Voucher> insertedVoucher = FileVoucherRepository.findById(newFixedVoucher.getVoucherId());
        assertThat(insertedVoucher.isEmpty(),is(false));
        assertThat(insertedVoucher.get(),samePropertyValuesAs(newFixedVoucher));
    }


    @Test
    @DisplayName("파일 바우처의 모든 바우처를 가져올 수 있다.")
    @Order(3)
    void findAll() {
        List<Voucher> allVouchers = FileVoucherRepository.findAll();
        assertThat(allVouchers.size(),is(1));

        FileVoucherRepository.insert(newPercentVoucher);

        List<Voucher> afterInsertAllVouchers = FileVoucherRepository.findAll();
        assertThat(afterInsertAllVouchers.size(),is(2));
        assertThat(afterInsertAllVouchers
                ,containsInAnyOrder(samePropertyValuesAs(newFixedVoucher), samePropertyValuesAs(newPercentVoucher)));
    }
}