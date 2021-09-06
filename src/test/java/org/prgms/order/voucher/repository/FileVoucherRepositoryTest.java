package org.prgms.order.voucher.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.order.voucher.entity.Voucher;
import org.prgms.order.voucher.entity.VoucherCreateStretage;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FileVoucherRepositoryTest {

    @Test
    @DisplayName("파일 정보에서 id로 바우처를 조회할 수 있다.")
    void testFindById() {
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        VoucherRepository fileVoucherRepository = new FileVoucherRepository();
        Voucher voucher1 = voucherCreateStretage.createVoucher("Fixed", UUID.randomUUID(), 1000);
        Voucher voucher2 = voucherCreateStretage.createVoucher("Percent", UUID.randomUUID(),50);
        fileVoucherRepository.insert(voucher1);
        fileVoucherRepository.insert(voucher2);

        assertThat(fileVoucherRepository.findById(voucher1.getVoucherId()).get(),samePropertyValuesAs(voucher1));
    }


    @Test
    @DisplayName("바우처를 파일에 추가할 수 있다.")
    void testInsert() {
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        VoucherRepository voucherRepository = new FileVoucherRepository();
        voucherRepository.insert(voucherCreateStretage.createVoucher("Fixed", UUID.randomUUID(), 1000));
        voucherRepository.insert(voucherCreateStretage.createVoucher("Fixed", UUID.randomUUID(), 1000));

    }

    @Test
    @DisplayName("파일 정보에서 모든 바우처를 조회할 수 있다.")
    void testFindAllVoucher() {
        VoucherCreateStretage voucherCreateStretage = new VoucherCreateStretage();
        VoucherRepository fileVoucherRepository = new FileVoucherRepository();
        Voucher voucher1 = voucherCreateStretage.createVoucher("Fixed", UUID.randomUUID(), 1000);
        fileVoucherRepository.insert(voucher1);
        fileVoucherRepository.findAllVoucher().forEach((item) -> assertThat(item,samePropertyValuesAs(voucher1)));
    }

}