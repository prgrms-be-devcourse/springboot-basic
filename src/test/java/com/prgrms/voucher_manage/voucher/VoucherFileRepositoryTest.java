package com.prgrms.voucher_manage.voucher;

import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.domain.voucher.repository.FileVoucherRepository;
import com.prgrms.voucher_manage.file.VoucherFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherFileRepositoryTest {
    FileVoucherRepository repository = new FileVoucherRepository(new VoucherFileManager("/src/test/resources/voucher_test.csv"));

    @BeforeEach
    public void clear(){
        repository.clear();
    }

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    public void memoryVoucherRepository_save(){
        //given
        Voucher voucher = new PercentDiscountVoucher(10L);
        //when
        Voucher savedVoucher = repository.save(voucher);
        //then
        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Voucher 리스트를 반환받을 수 있다.")
    public void memoryVoucherRepository_findAll(){
        //given
        Voucher voucher1 = new PercentDiscountVoucher(10L);
        Voucher voucher2 = new PercentDiscountVoucher(20L);

        repository.save(voucher1);
        repository.save(voucher2);

        //when
        List<Voucher> voucherList = repository.findAll();
        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }
}
