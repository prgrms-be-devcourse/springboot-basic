package org.prgrms.vouchermanager.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@Slf4j
@SpringBootTest
class JdbcVoucherRepositoryTest {


    private final JdbcVoucherRepository repository;

    @Autowired
    JdbcVoucherRepositoryTest(JdbcVoucherRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("바우처 저장에 성공해야한다.")
    void save() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 20, VoucherType.FIXED);
        Voucher voucher = repository.save(fixedAmountVoucher);

        Optional<Voucher> voucher1 = repository.findByID(voucher.getVoucherId());

        assertThat(voucher.getVoucherId()).isEqualTo(voucher1.get().getVoucherId());
    }

    @Test
    @DisplayName("두 바우처 저장 시, 전체 사이즈가 2여야 한다.")
    void findAll() {
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 20, VoucherType.FIXED);
        repository.save(fixedAmountVoucher1);
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 20, VoucherType.FIXED);
        repository.save(fixedAmountVoucher2);

        List<Voucher> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id로 데이터를 삭제할 수 있어야한다")
    void deleteById(){
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 20, VoucherType.FIXED);
        Voucher voucher = repository.save(fixedAmountVoucher1);
        List<Voucher> before = repository.findAll();

        Optional<Voucher> resultVoucher = repository.deleteById(voucher.getVoucherId());

        assertThat(resultVoucher).isEmpty();
    }
}