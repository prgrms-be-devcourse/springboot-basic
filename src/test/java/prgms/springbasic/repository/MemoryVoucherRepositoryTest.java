package prgms.springbasic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import prgms.springbasic.voucher.FixedAmountVoucher;
import prgms.springbasic.voucher.MemoryVoucherRepository;
import prgms.springbasic.voucher.PercentDiscountVoucher;
import prgms.springbasic.voucher.Voucher;

import java.util.List;
import java.util.UUID;

class MemoryVoucherRepositoryTest {

    @Test
    void findById() {
        MemoryVoucherRepository repository = new MemoryVoucherRepository();
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentDiscountVoucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        repository.save(voucher1);
        repository.save(voucher2);

        Voucher foundVoucher = repository.findById(voucher1.getVoucherId()).get();
        Assertions.assertThat(foundVoucher.getDiscountValue()).isEqualTo(voucher1.getDiscountValue());
    }

    @Test
    void getVoucherList() {
        MemoryVoucherRepository repository = new MemoryVoucherRepository();
        List<Voucher> voucherList = repository.getVoucherList();
        Assertions.assertThat(voucherList).isEmpty();
    }
}