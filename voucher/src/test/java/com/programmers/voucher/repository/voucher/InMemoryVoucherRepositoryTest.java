package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class InMemoryVoucherRepositoryTest {

    InMemoryVoucherRepository inMemoryVoucherRepository = new InMemoryVoucherRepository();

    @Test
    @DisplayName("Load, Persist Vouchers Test")
    void loadAndPersist() {
        assertThrows(UnsupportedOperationException.class, () -> inMemoryVoucherRepository.loadVouchers());
        assertThrows(UnsupportedOperationException.class, () -> inMemoryVoucherRepository.persistVouchers());
    }

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);

        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));
        assertEquals(voucherName, voucher.getName());
        assertNotNull(voucher.getCreatedAt());
        assertEquals(discountPolicy.getType(), voucher.getDiscountPolicy().getType());
        assertEquals(discountPolicy.getAmount(), voucher.getDiscountPolicy().getAmount());

        assertEquals(price - discountPolicy.getAmount(), discountPolicy.discount(price));
    }

    @Test
    @DisplayName("Voucher Read Test")
    void readVoucher() {
        String voucherName = "voucherReadme";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, 1234));

        final Optional<Voucher> byId = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucher, byId.get());

    }

    @Test
    @DisplayName("Vouchers Read Test")
    void readVouchers() {
        String voucherName = "voucherReadme";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher1 = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, 1111));
        final Voucher voucher2 = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, 1111));
        final Voucher voucher3 = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, 1111));

        final List<Voucher> allByCustomer = inMemoryVoucherRepository.findAllByCustomer(1111);
        assertEquals(3, allByCustomer.size());
        assertEquals(voucher1.getId(), allByCustomer.get(0).getId());
        assertEquals(voucher2.getId(), allByCustomer.get(1).getId());
        assertEquals(voucher3.getId(), allByCustomer.get(2).getId());
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));

        discountPolicy.updateAmount(5000);
        inMemoryVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.updateType(DiscountType.PERCENTAGE);
        inMemoryVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountType.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountType.FIXED), -1));
        inMemoryVoucherRepository.deleteById(voucher.getId());
        assertTrue(inMemoryVoucherRepository.findById(voucher.getId()).isEmpty());
    }

    @Test
    @DisplayName("List All Vouchers Test")
    void listAll() {
        Voucher voucher1 = inMemoryVoucherRepository.save(new Voucher("voucher1", new DiscountPolicy(2500, DiscountType.FIXED), 0));
        Voucher voucher2 = inMemoryVoucherRepository.save(new Voucher("voucher2", new DiscountPolicy(2500, DiscountType.FIXED), 0));
        Voucher voucher3 = inMemoryVoucherRepository.save(new Voucher("voucher3", new DiscountPolicy(2500, DiscountType.FIXED), 0));
        Voucher voucher4 = inMemoryVoucherRepository.save(new Voucher("voucher4", new DiscountPolicy(2500, DiscountType.FIXED), 0));
        Voucher voucher5 = inMemoryVoucherRepository.save(new Voucher("voucher5", new DiscountPolicy(2500, DiscountType.FIXED), 0));

        List<Voucher> vouchers = inMemoryVoucherRepository.listAll();
        assertEquals(5, vouchers.size());
        assertEquals(voucher1, vouchers.get(0));
        assertEquals(voucher2, vouchers.get(1));
        assertEquals(voucher3, vouchers.get(2));
        assertEquals(voucher4, vouchers.get(3));
        assertEquals(voucher5, vouchers.get(4));
    }
}
