package org.prgrms.vouchermanager.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
class MemoryVoucherRepositoryTest {
    VoucherRepository repository = new MemoryVoucherRepository();
    @Test
    @DisplayName("바우처가 성공적으로 저장되어야 한다.")
    public void save(){
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        Voucher voucher = repository.save(new FixedAmountVoucher(voucherId, 10L));
        Optional<Voucher> findVoucher = repository.findByID(voucherId);
        //then
        assertThat(findVoucher.get()).isEqualTo(voucher);
    }

    @Test
    @DisplayName("존재하는 모든 바우처를 조회할 수 있어야한다.")
    void findAll(){
        Voucher voucher1 = repository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        Voucher voucher2 = repository.save(new FixedAmountVoucher(UUID.randomUUID(), 20L));

        List<Voucher> all = repository.findAll();

        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("없는 ID 조회 시 null이 리턴되어야한다.")
    void findByNullId(){
        Optional<Voucher> voucher = repository.findByID(UUID.randomUUID());
        assertThat(voucher).isEmpty();
    }



}