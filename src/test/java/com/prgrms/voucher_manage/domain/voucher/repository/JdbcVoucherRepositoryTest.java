package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static org.assertj.core.api.Assertions.assertThat;
@JdbcTest
@ActiveProfiles({"prod","test"})
@Import(JdbcVoucherRepository.class)
public class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository repository;


    @Test
    @DisplayName("voucher를 저장할 수 있다.")
    public void jdbcVoucherRepository_save() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000L);
        //when
        Voucher savedVoucher = repository.save(voucher);
        //then
        assertThat(voucher).isNotNull();
        assertThat(voucher.getDiscountAmount()).isEqualTo(1000L);
        assertThat(savedVoucher.getType()).isEqualTo(FIXED);
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
    public void jdbcVoucherRepository_findVoucher(){
        //given
        Voucher voucher1 = saveVoucher();
        Voucher voucher2 = saveVoucher();
        //when
        List<Voucher> vouchers = repository.findAll();
        //then
        assertThat(vouchers.size()).isEqualTo(2);
        assertThat(voucher1.getId()).isEqualTo(vouchers.get(0).getId());
        assertThat(voucher2.getId()).isEqualTo(vouchers.get(1).getId());
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
        Voucher updatedVoucher = new FixedAmountVoucher(voucher.getId(), 2000L);
        int update = repository.update(updatedVoucher);
        //then
        assertThat(update).isEqualTo(1);
        assertThat(updatedVoucher.getDiscountAmount()).isEqualTo(2000L);

    }

    public Voucher saveVoucher(){
        return repository.save(new FixedAmountVoucher(1000L));
    }
}
