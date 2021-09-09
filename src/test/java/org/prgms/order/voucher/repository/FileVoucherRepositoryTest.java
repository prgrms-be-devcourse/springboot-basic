package org.prgms.order.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgms.order.voucher.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileVoucherRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);

    @Test
    @Order(1)
    @DisplayName("파일 정보에서 모든 바우처를 조회할 수 있다.")
    void testFindAllVoucher() {
        //given
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        VoucherRepository fileVoucherRepository = new FileVoucherRepository();

        //when
        var size = fileVoucherRepository.findAll().size();
        Voucher voucher1 = voucherCreateStretage.createVoucher(VoucherIndexType.FIXED, new VoucherData(UUID.randomUUID(),20,LocalDateTime.now().withNano(0)));
        fileVoucherRepository.insert(voucher1);

        //then
        assertThat(size+1, is(fileVoucherRepository.findAll().size()));
    }


    @Test
    @Order(2)
    @DisplayName("바우처를 파일에 추가할 수 있다.")
    void testInsert() {
        VoucherRepository voucherRepository = new FileVoucherRepository();
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        voucherRepository.insert(voucherCreateStretage.createVoucher(VoucherIndexType.FIXED, new VoucherData(UUID.randomUUID(),1000,LocalDateTime.now().withNano(0))));
        voucherRepository.insert(voucherCreateStretage.createVoucher(VoucherIndexType.PERCENT, new VoucherData(UUID.randomUUID(),30,LocalDateTime.now().withNano(0))));

    }


    @Test
    @Order(3)
    @DisplayName("파일 정보에서 id로 바우처를 조회할 수 있다.")
    void testFindById() {
        VoucherRepository fileVoucherRepository = new FileVoucherRepository();
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        Voucher voucher1 = voucherCreateStretage.createVoucher(VoucherIndexType.FIXED, new VoucherData(UUID.randomUUID(),1000,LocalDateTime.now().withNano(0)));
        Voucher voucher2 = voucherCreateStretage.createVoucher(VoucherIndexType.PERCENT, new VoucherData(UUID.randomUUID(),30,LocalDateTime.now().withNano(0)));
        fileVoucherRepository.insert(voucher1);
        fileVoucherRepository.insert(voucher2);

        var actual = fileVoucherRepository.findById(voucher1.getVoucherId()).get();

        assertThat(actual.getVoucherId(),is(voucher1.getVoucherId()));
        assertThat(actual.getType(),is(voucher1.getType()));
        assertThat(actual.getAmount(),is(voucher1.getAmount()));
    }


}