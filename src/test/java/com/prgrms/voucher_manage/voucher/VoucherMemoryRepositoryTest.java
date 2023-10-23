package com.prgrms.voucher_manage.voucher;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static org.assertj.core.api.Assertions.assertThat;

public class VoucherMemoryRepositoryTest {
    private final MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    public void memoryVoucherRepository_save(){
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(1000L);
        //when
        Voucher savedVoucher = repository.save(fixedAmountVoucher);
        //then
        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getVoucherType()).isEqualTo(FIXED);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("Voucher 리스트를 반환받을 수 있다.")
    public void memoryVoucherRepository_findAll(){
        //given
        Voucher fixedAmountVoucher1 = new FixedAmountVoucher(1000L);
        Voucher fixedAmountVoucher2 = new FixedAmountVoucher(1000L);

        repository.save(fixedAmountVoucher1);
        repository.save(fixedAmountVoucher2);

        //when
        List<Voucher> voucherList = repository.findAll();
        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }
}
