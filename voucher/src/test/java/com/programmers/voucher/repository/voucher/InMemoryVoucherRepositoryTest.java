package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
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
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);

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
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));

        final Optional<Voucher> byId = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucherName, byId.get().getName());

        final List<Voucher> allByCustomer = inMemoryVoucherRepository.findAllByCustomer(-1);
        assertEquals(1, allByCustomer.size());
        assertEquals(byId.get(), allByCustomer.get(0));
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));

        discountPolicy.setAmount(5000);
        inMemoryVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.setType(DiscountPolicy.Type.PERCENTAGE);
        inMemoryVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = inMemoryVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountPolicy.Type.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Voucher voucher = inMemoryVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountPolicy.Type.FIXED), -1));
        inMemoryVoucherRepository.deleteById(voucher.getId());
        assertTrue(inMemoryVoucherRepository.findById(voucher.getId()).isEmpty());
    }

}
