package com.prgrms.repository.voucher;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    private MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void setUp() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    public void testFindById_ExistingVoucherId_ReturnsVoucher() {
        UUID voucherId1 = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FIXED_AMOUNT_VOUCHER);
        UUID voucherId = voucher.getVoucherId();

        voucherRepository.insert(voucher);

        Optional<Voucher> result = voucherRepository.findById(voucherId);

        assertTrue(result.isPresent());
        assertEquals(voucher, result.get());
    }

    @Test
    public void testFindById_NonExistingVoucherId_ReturnsEmptyOptional() {
        UUID voucherId = UUID.randomUUID();

        Optional<Voucher> result = voucherRepository.findById(voucherId);

        assertFalse(result.isPresent());
    }

    @Test
    public void testInsert_InsertedVoucher() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, new Discount(20), VoucherPolicy.FIXED_AMOUNT_VOUCHER);

        Voucher result = voucherRepository.insert(voucher);

        assertNotNull(result);
        assertEquals(voucher, result);
    }

    @Test
    public void testGetAllVoucherList_AllVouchers() {
        UUID voucherId1 = UUID.randomUUID();
        UUID voucherId2 = UUID.randomUUID();

        Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherPolicy.FIXED_AMOUNT_VOUCHER);
        Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId2, new Discount(20), VoucherPolicy.PERCENT_DISCOUNT_VOUCHER);
        voucherRepository.insert(createdVoucher1);
        voucherRepository.insert(createdVoucher2);

        VoucherList result = voucherRepository.getAllVoucherList();

        VoucherResponse voucherResponse1 = VoucherResponse.of(createdVoucher1);
        VoucherResponse voucherResponse2 = VoucherResponse.of(createdVoucher2);

        assertNotNull(result);
        assertEquals(2, result.convertVoucherResponse().size());
        assertTrue(result.convertVoucherResponse().contains(voucherResponse1));
        assertTrue(result.convertVoucherResponse().contains(voucherResponse2));
    }
}