package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class BasicVoucherServiceTest {

    VoucherRepository voucherRepository = mock(VoucherRepository.class);

    VoucherService voucherService = new BasicVoucherService(voucherRepository);

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        long customerId = 1;

        String voucherFixedName = "voucherCreateFixed";
        DiscountType voucherFixedType = DiscountType.FIXED;
        int voucherFixedAmount = 2500;
        DiscountPolicy fixedDiscountPolicy = new DiscountPolicy(voucherFixedAmount, voucherFixedType);
        when(voucherRepository.save(new Voucher(voucherFixedName, fixedDiscountPolicy, customerId)))
                .thenReturn(new Voucher(1, voucherFixedName, fixedDiscountPolicy, LocalDate.now(), customerId));

        final Voucher voucherFixed = voucherService.create(voucherFixedName, voucherFixedType, voucherFixedAmount, customerId);

        assertEquals(voucherFixedName, voucherFixed.getName());
        assertEquals(voucherFixedType, voucherFixed.getDiscountPolicy().getType());
        assertEquals(voucherFixedAmount, voucherFixed.getDiscountPolicy().getAmount());
        verify(voucherRepository).save(new Voucher(voucherFixedName, fixedDiscountPolicy, customerId));

        String voucherPercentageName = "voucherCreatePercentage";
        DiscountType voucherPercentageType = DiscountType.PERCENTAGE;
        int voucherPercentageAmount = 50;
        final DiscountPolicy percentageDiscountPolicy = new DiscountPolicy(voucherPercentageAmount, voucherPercentageType);
        when(voucherRepository.save(new Voucher(voucherPercentageName, percentageDiscountPolicy, customerId)))
                .thenReturn(new Voucher(2, voucherPercentageName, percentageDiscountPolicy, LocalDate.now(), customerId));

        final Voucher voucherPercentage = voucherService.create(voucherPercentageName, voucherPercentageType, voucherPercentageAmount, customerId);

        assertEquals(voucherPercentageName, voucherPercentage.getName());
        assertEquals(voucherPercentageType, voucherPercentage.getDiscountPolicy().getType());
        assertEquals(voucherPercentageAmount, voucherPercentage.getDiscountPolicy().getAmount());
        verify(voucherRepository).save(new Voucher(voucherPercentageName, percentageDiscountPolicy, customerId));
    }

    @Test
    @DisplayName("Voucher Creation Constraints Test")
    void constraintCreate() {
        String fixedVoucherName = "FixedVoucherName";
        final DiscountPolicy discountPolicyFixed = new DiscountPolicy(0, DiscountType.FIXED);
        when(voucherRepository.save(new Voucher(fixedVoucherName, discountPolicyFixed, 1)))
                .thenReturn(new Voucher(1111, fixedVoucherName, discountPolicyFixed, LocalDate.now(), 1));

        final Voucher voucherFixed = voucherService.create(fixedVoucherName, DiscountType.FIXED, -2500, 1);

        verify(voucherRepository).save(new Voucher(fixedVoucherName, discountPolicyFixed, 1));
        assertEquals(DiscountType.FIXED, voucherFixed.getDiscountPolicy().getType());
        assertEquals(0, voucherFixed.getDiscountPolicy().getAmount());

        String percentageVoucherName = "PercentageVoucherName";
        final DiscountPolicy discountPolicyPercentage1 = new DiscountPolicy(100, DiscountType.PERCENTAGE);
        when(voucherRepository.save(new Voucher(percentageVoucherName, discountPolicyPercentage1, 2)))
                .thenReturn(new Voucher(2222, percentageVoucherName, discountPolicyPercentage1, LocalDate.now(), 2));

        final Voucher voucherPercentage1 = voucherService.create(percentageVoucherName, DiscountType.PERCENTAGE, 250, 2);

        verify(voucherRepository).save(new Voucher(percentageVoucherName, discountPolicyPercentage1, 2));
        assertEquals(DiscountType.PERCENTAGE, voucherPercentage1.getDiscountPolicy().getType());
        assertEquals(100, voucherPercentage1.getDiscountPolicy().getAmount());

        final DiscountPolicy discountPolicyPercentage2 = new DiscountPolicy(0, DiscountType.PERCENTAGE);
        when(voucherRepository.save(new Voucher(percentageVoucherName, discountPolicyPercentage2, 2)))
                .thenReturn(new Voucher(3333, percentageVoucherName, discountPolicyPercentage2, LocalDate.now(), 2));

        final Voucher voucherPercentage2 = voucherService.create(percentageVoucherName, DiscountType.PERCENTAGE, -250, 2);

        verify(voucherRepository).save(new Voucher(percentageVoucherName, discountPolicyPercentage2, 2));
        assertEquals(DiscountType.PERCENTAGE, voucherPercentage2.getDiscountPolicy().getType());
        assertEquals(0, voucherPercentage2.getDiscountPolicy().getAmount());
    }

    @Test
    @DisplayName("Voucher Read Test")
    void readVoucher() {
        String voucherName = "readMe";
        DiscountType voucherType = DiscountType.FIXED;
        int voucherAmount = 2500;
        final DiscountPolicy discountPolicy = new DiscountPolicy(voucherAmount, voucherType);
        long voucherId = 1234;
        when(voucherRepository.findById(voucherId))
                .thenReturn(Optional.of(new Voucher(voucherId, voucherName, discountPolicy, LocalDate.now(), 1111)));

        final Optional<Voucher> byId = voucherService.findById(voucherId);

        verify(voucherRepository).findById(voucherId);
        assertTrue(byId.isPresent());
        assertEquals(voucherName, byId.get().getName());
        assertEquals(discountPolicy, byId.get().getDiscountPolicy());
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        Voucher beforeNameUpdate = new Voucher(1, "updateMe", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1111);
        Voucher afterNameUpdate = new Voucher(1, "updated", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1111);

        when(voucherRepository.update(afterNameUpdate))
                .thenReturn(afterNameUpdate);

        final Voucher nameUpdatedVoucher = voucherService.update(afterNameUpdate);

        verify(voucherRepository).update(afterNameUpdate);
        assertNotEquals(beforeNameUpdate, nameUpdatedVoucher);
        assertEquals(afterNameUpdate, nameUpdatedVoucher);

        Voucher beforeTypeUpdate = new Voucher(2, "typeUpdateVoucher", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 2222);
        Voucher afterTypeUpdate = new Voucher(2, "typeUpdateVoucher", new DiscountPolicy(100, DiscountType.PERCENTAGE), LocalDate.now(), 2222);

        when(voucherRepository.update(afterTypeUpdate))
                .thenReturn(afterTypeUpdate);

        final Voucher typeUpdatedVoucher = voucherService.update(afterTypeUpdate);

        verify(voucherRepository).update(afterTypeUpdate);
        assertNotEquals(beforeTypeUpdate, typeUpdatedVoucher);
        assertEquals(afterTypeUpdate, typeUpdatedVoucher);

        Voucher beforeAmountUpdate = new Voucher(3, "amountUpdateVoucher", new DiscountPolicy(500, DiscountType.FIXED), LocalDate.now(), 3333);
        Voucher afterAmountUpdate = new Voucher(3, "amountUpdateVoucher", new DiscountPolicy(5000, DiscountType.FIXED), LocalDate.now(), 3333);

        when(voucherRepository.update(afterAmountUpdate))
                .thenReturn(afterAmountUpdate);

        final Voucher amountUpdatedVoucher = voucherService.update(afterAmountUpdate);

        verify(voucherRepository).update(amountUpdatedVoucher);
        assertNotEquals(beforeAmountUpdate, amountUpdatedVoucher);
        assertEquals(afterAmountUpdate, amountUpdatedVoucher);
    }

    @Test
    @DisplayName("Voucher Discount Policy Update Constraints Test")
    void constraintsUpdate() {
        Voucher fixedBefore = new Voucher(1, "typeUpdateVoucher", new DiscountPolicy(-2500, DiscountType.FIXED), LocalDate.now(), 2222);
        Voucher fixedAfter = new Voucher(1, "typeUpdateVoucher", new DiscountPolicy(0, DiscountType.FIXED), LocalDate.now(), 2222);
        when(voucherRepository.update(fixedAfter))
                .thenReturn(fixedAfter);

        final Voucher constrainedFixedVoucher = voucherService.update(fixedBefore);

        verify(voucherRepository).update(fixedAfter);
        assertNotEquals(-2500, constrainedFixedVoucher.getDiscountPolicy().getAmount());
        assertEquals(0, constrainedFixedVoucher.getDiscountPolicy().getAmount());

        Voucher percentageBefore1 = new Voucher(2, "typeUpdateVoucher", new DiscountPolicy(-250, DiscountType.PERCENTAGE), LocalDate.now(), 3333);
        Voucher percentageAfter1 = new Voucher(2, "typeUpdateVoucher", new DiscountPolicy(0, DiscountType.PERCENTAGE), LocalDate.now(), 3333);
        when(voucherRepository.update(percentageAfter1))
                .thenReturn(percentageAfter1);

        final Voucher constrainedPercentageVoucher1 = voucherService.update(percentageBefore1);

        verify(voucherRepository).update(percentageAfter1);
        assertNotEquals(-250, constrainedPercentageVoucher1.getDiscountPolicy().getAmount());
        assertEquals(0, constrainedPercentageVoucher1.getDiscountPolicy().getAmount());

        Voucher percentageBefore2 = new Voucher(3, "typeUpdateVoucher", new DiscountPolicy(250, DiscountType.PERCENTAGE), LocalDate.now(), 4444);
        Voucher percentageAfter2 = new Voucher(3, "typeUpdateVoucher", new DiscountPolicy(100, DiscountType.PERCENTAGE), LocalDate.now(), 4444);
        when(voucherRepository.update(percentageAfter2))
                .thenReturn(percentageAfter2);

        final Voucher constrainedPercentageVoucher2 = voucherService.update(percentageBefore2);

        verify(voucherRepository).update(percentageAfter2);
        assertNotEquals(250, constrainedPercentageVoucher2.getDiscountPolicy().getAmount());
        assertEquals(100, constrainedPercentageVoucher2.getDiscountPolicy().getAmount());
    }

    // no deletion test because service method 'delete' returns nothing and just calls repository's delete(or deleteById) method.

    @Test
    @DisplayName("Vouchers List Test")
    void listAllVouchers() {
        List<Voucher> list = new ArrayList<>(10);
        for(int i=0;i<10;i++) {
            list.add(new Voucher(i + 1, "voucherName" + i, new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1000 + i));
        }
        when(voucherRepository.listAll())
                .thenReturn(list);

        final List<Voucher> vouchers = voucherService.listAll();

        verify(voucherRepository).listAll();
        assertEquals(10, vouchers.size());
        for(int i=0;i<10;i++) {
            Voucher compare = new Voucher(i + 1, "voucherName" + i, new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1000 + i);
            assertEquals(compare, vouchers.get(i));
        }
    }

    @Test
    @DisplayName("Discount Operation Test")
    void discount() {
        Voucher fixedVoucher = new Voucher("voucher", new DiscountPolicy(2500, DiscountType.FIXED), 1234);
        assertEquals(7500, fixedVoucher.discount(10000));

        Voucher percentageVoucher = new Voucher("voucher", new DiscountPolicy(25, DiscountType.PERCENTAGE), 5678);
        assertEquals(7500, percentageVoucher.discount(10000));
    }
}
