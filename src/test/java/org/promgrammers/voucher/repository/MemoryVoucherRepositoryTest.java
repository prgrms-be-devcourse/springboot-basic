package org.promgrammers.voucher.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.promgrammers.voucher.domain.FixedAmountVoucher;
import org.promgrammers.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository repository;

    @BeforeEach
    void before() {
        repository = new MemoryVoucherRepository();
    }

    @Test
    void testFindById() {
        // Given
        long amount = 100;
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(amount, id);
        repository.save(voucher);

        // When
        Optional<Voucher> foundVoucher = repository.findById(id);

        // Then
        Assertions.assertTrue(foundVoucher.isPresent());
        Assertions.assertEquals(voucher, foundVoucher.get());
    }

    @Test
    void testFindAll() {
        // Given
        long amount1 = 100;
        UUID id1 = UUID.randomUUID();
        Voucher voucher1 = new FixedAmountVoucher(amount1, id1);

        long amount2 = 50;
        UUID id2 = UUID.randomUUID();
        Voucher voucher2 = new FixedAmountVoucher(amount2, id2);

        repository.save(voucher1);
        repository.save(voucher2);

        // When
        List<Voucher> vouchers = repository.findAll();

        // Then
        Assertions.assertEquals(2, vouchers.size());
        Assertions.assertTrue(vouchers.contains(voucher1));
        Assertions.assertTrue(vouchers.contains(voucher2));
    }

    @Test
    void testSave() {
        // Given
        long amount = 100;
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(amount, id);

        // When
        Voucher savedVoucher = repository.save(voucher);

        // Then
        Assertions.assertEquals(voucher, savedVoucher);
    }

    @Test
    void testDeleteAll() {
        // Given
        long amount = 100;
        UUID id = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(amount, id);
        repository.save(voucher);

        // When
        repository.deleteAll();

        // Then
        List<Voucher> vouchers = repository.findAll();
        Assertions.assertTrue(vouchers.isEmpty());
    }
}