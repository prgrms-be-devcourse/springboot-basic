package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

public class MemoryVoucherRepositoryTest {
    private final MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    public void memoryVoucherRepository_save(){
        //given
        Voucher  percentDiscountVoucher = new PercentDiscountVoucher(1000L);
        //when
        Voucher savedVoucher = repository.save(percentDiscountVoucher);
        //then
        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getType()).isEqualTo(PERCENT);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("voucher를 id로 찾을 수 있다.")
    public void jdbcVoucherRepository_findById(){
        //given
        Voucher voucher = saveVoucher();
        //when
        Voucher foundVoucher = repository.findById(voucher.getId()).orElse(null);
        //then
        assertThat(foundVoucher).isNotNull();
        assertThat(foundVoucher.getId()).isEqualTo(voucher.getId());
        assertThat(foundVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("모든 voucher를 찾을 수 있다.")
    public void memoryVoucherRepository_findAll(){
        //given
        Voucher percentDiscountVoucher1 = new PercentDiscountVoucher(1000L);
        Voucher percentDiscountVoucher2 = new PercentDiscountVoucher(1000L);

        repository.save(percentDiscountVoucher1);
        repository.save(percentDiscountVoucher2);

        //when
        List<Voucher> voucherList = repository.findAll();
        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("voucher를 삭제할 수 있다.")
    public void jdbcVoucherRepository_deleteById(){
        //given
        Voucher voucher = saveVoucher();
        //when
        int delete = repository.deleteById(voucher.getId());
        //then
        assertThat(delete).isEqualTo(1);
        assertThat(repository.findById(voucher.getId())).isEmpty();
    }


    @Test
    @DisplayName("voucher를 업데이트할 수 있다.")
    public void jdbcVoucherRepository_update(){
        //given
        Voucher voucher = saveVoucher();
        //when
        Voucher updatedVoucher = new PercentDiscountVoucher(voucher.getId(), 2000L);
        int update = repository.update(updatedVoucher);
        //then
        assertThat(update).isEqualTo(1);
        assertThat(updatedVoucher.getDiscountAmount()).isEqualTo(2000L);

    }

    public Voucher saveVoucher(){
        return repository.save(new PercentDiscountVoucher(1000L));
    }

}
