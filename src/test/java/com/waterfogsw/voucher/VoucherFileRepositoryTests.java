package com.waterfogsw.voucher;

import com.waterfogsw.voucher.voucher.FileVoucherRepository;
import com.waterfogsw.voucher.voucher.Voucher;
import com.waterfogsw.voucher.voucher.VoucherRepository;
import com.waterfogsw.voucher.voucher.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class VoucherFileRepositoryTests {
    VoucherRepository voucherRepository = new FileVoucherRepository();
    //바우처 레포지토리
    @Test
    @DisplayName("파일 - 바우처 레포지토리에 저장")
    public void fileRepositoryVoucherSave() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), 10d, VoucherType.PERCENT_DISCOUNT);
        voucherRepository.save(voucher);

        //when
        Voucher find = voucherRepository.findById(voucher.getVoucherId());

        //then
        Assertions.assertThat(voucher).isEqualTo(find);
    }

    @Test
    @DisplayName("파일 - 바우처 리스트 조회")
    public void fileRepositoryVoucherListLookUp() {
        //given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), 10d, VoucherType.PERCENT_DISCOUNT);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), 10d, VoucherType.PERCENT_DISCOUNT);
        Voucher voucher3 = new Voucher(UUID.randomUUID(), 10d, VoucherType.PERCENT_DISCOUNT);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        //when
        List<Voucher> list = voucherRepository.findAll();

        //then
        Assertions.assertThat(list.size()).isEqualTo(3);
    }
}
