package org.prgrms.vouchermanager.Repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
class MemoryVoucherRepositoryTest {
    VoucherRepository repository = new MemoryVoucherRepository();
    @Test
    public void save(){
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        Voucher voucher = repository.save(new FixedAmountVoucher(voucherId, 10L));
        Optional<Voucher> findVoucher = repository.findByID(voucherId);
        //then
        assertThat(findVoucher.get()).isEqualTo(voucher);
    }

}