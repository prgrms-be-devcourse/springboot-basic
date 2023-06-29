package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    public MemoryVoucherRepository memoryVoucherRepository;

    @BeforeEach
    public void setup() {
        memoryVoucherRepository = new MemoryVoucherRepository();
    }

    @Test
    public void findByIdTest_존재하는_바우처_조회() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId);
        memoryVoucherRepository.insert(voucher);

        Optional<Voucher> findVoucher = memoryVoucherRepository.findById(voucherId);
        UUID findVoucherId = findVoucher.get().getVoucherId();

        assertThat(findVoucherId, is(voucherId));
    }

    @Test
    public void findByIdTest_존재하지_않는_바우처_조회() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId);
        memoryVoucherRepository.insert(voucher);

        UUID differentVoucherId = UUID.randomUUID();
        Optional<Voucher> findVoucher = memoryVoucherRepository.findById(differentVoucherId);

        assertThrows(DatabaseException.class, () -> {
            findVoucher.orElseThrow(DatabaseException::new);
        });
    }

    @Test
    public void insertTest(){
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherId);
        memoryVoucherRepository.insert(voucher);

        Optional<Voucher> findVoucher = memoryVoucherRepository.findById(voucherId);

        assertThat(findVoucher.get(), is(voucher));
    }

    @Test
    public void findAllTest(){
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(UUID.randomUUID());
        memoryVoucherRepository.insert(voucher1);
        FixedAmountVoucher voucher2 = new FixedAmountVoucher(UUID.randomUUID());
        memoryVoucherRepository.insert(voucher2);

        List<Voucher> voucherList = memoryVoucherRepository.findAll();

        assertThat(voucherList, contains(voucher1, voucher2));
    }
}