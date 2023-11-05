package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemoryVoucherRepositoryTest {
    private final MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    public void memoryVoucherRepository_save() {
        //given
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(1000L);
        //when
        Voucher savedVoucher = repository.save(percentDiscountVoucher);
        //then
        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getType()).isEqualTo(PERCENT);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("voucher를 id로 찾을 수 있다.")
    public void jdbcVoucherRepository_getById() {
        //given
        Voucher voucher = saveVoucher();
        //when
        Voucher foundVoucher = repository.getById(voucher.getId());
        //then
        assertThat(foundVoucher).isNotNull();
        assertThat(foundVoucher.getId()).isEqualTo(voucher.getId());
        assertThat(foundVoucher.getDiscountAmount()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("모든 voucher를 찾을 수 있다.")
    public void memoryVoucherRepository_getAll() {
        //given
        Voucher percentDiscountVoucher1 = new PercentDiscountVoucher(1000L);
        Voucher percentDiscountVoucher2 = new PercentDiscountVoucher(1000L);

        repository.save(percentDiscountVoucher1);
        repository.save(percentDiscountVoucher2);

        //when
        List<Voucher> voucherList = repository.getAll();
        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("voucher를 타입으로 조회할 수 있다.")
    public void jdbcVoucherRepository_getByType(){
        //given
        Voucher voucher1 = saveVoucher();
        //when
        List<Voucher> vouchers = repository.getByType(PERCENT);
        //then
        assertThat(vouchers.size()).isEqualTo(1);
        assertThat(vouchers.get(0).getId()).isEqualTo(voucher1.getId());
    }

    @Test
    @DisplayName("voucher를 삭제할 수 있다.")
    public void jdbcVoucherRepository_deleteById() {
        //given
        Voucher voucher = saveVoucher();
        //when
        repository.deleteById(voucher.getId());
        //then
        RuntimeException e = assertThrows(RuntimeException.class, () -> repository.getById(voucher.getId()));
        assertThat(e.getMessage()).isEqualTo("해당 아이디를 가진 바우처가 존재하지 않습니다.");
    }


    @Test
    @DisplayName("voucher를 업데이트할 수 있다.")
    public void jdbcVoucherRepository_update() {
        //given
        Voucher voucher = saveVoucher();
        //when
        Voucher updatedVoucher = new PercentDiscountVoucher(voucher.getId(), 2000L);
        //then
        assertThat(updatedVoucher.getDiscountAmount()).isEqualTo(2000L);

    }

    public Voucher saveVoucher() {
        return repository.save(new PercentDiscountVoucher(1000L));
    }

}
