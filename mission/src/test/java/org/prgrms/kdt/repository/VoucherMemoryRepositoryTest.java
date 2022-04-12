package org.prgrms.kdt.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.models.FixedAmountVoucher;
import org.prgrms.kdt.models.Voucher;

import java.util.List;
import java.util.UUID;

public class VoucherMemoryRepositoryTest {

    VoucherRepository repository = new VoucherMemoryRepository();

    @Test
    @DisplayName("해시맵에 바우처 저장 기능")
    public void saveTest() {

        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 100);

        //when
        Voucher checkVoucher = repository.save(voucher1);

        //then
        Assertions.assertThat(checkVoucher).isEqualTo(voucher1);
    }

    @Test
    @DisplayName("해시맵 findAll 테스트")
    public void findAllTest() {

        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2);

        Voucher checkVoucher1 = repository.save(voucher1);
        Voucher checkVoucher2 = repository.save(voucher2);

        //when
        List<Voucher> list = repository.findAll();

        //then
        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0)).isEqualTo(checkVoucher1);
        Assertions.assertThat(list.get(1)).isEqualTo(checkVoucher2);
    }
}
