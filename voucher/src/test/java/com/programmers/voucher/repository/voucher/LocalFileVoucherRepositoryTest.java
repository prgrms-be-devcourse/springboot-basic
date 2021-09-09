package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LocalFileVoucherRepositoryTest {

    @Autowired
    VoucherRepository localFileVoucherRepository;

    @BeforeEach
    void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of("storage/test", "vouchers.db"));
    }

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);

        final Voucher voucher = localFileVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));
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
        final Voucher voucher = localFileVoucherRepository.save(new Voucher(voucherName, discountPolicy, -12345));

        final Optional<Voucher> byId = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucherName, byId.get().getName());

        final List<Voucher> allByCustomer = localFileVoucherRepository.findAllByCustomer(-12345);
//        assertEquals(1, allByCustomer.size()); --> JUnit test is independent but file doesn't.
        assertEquals(byId.get(), allByCustomer.get(0));
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);
        final Voucher voucher = localFileVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));

        discountPolicy.updateAmount(5000);
        localFileVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.updateType(DiscountPolicy.Type.PERCENTAGE);
        localFileVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountPolicy.Type.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Voucher voucher = localFileVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountPolicy.Type.FIXED), -1));
        localFileVoucherRepository.deleteById(voucher.getId());
        assertTrue(localFileVoucherRepository.findById(voucher.getId()).isEmpty());
    }

    @Test
    @DisplayName("Local File Input/Output Test")
    void fileIO() {
        final Voucher voucher = localFileVoucherRepository.save(new Voucher("fileIO", new DiscountPolicy(1000, DiscountPolicy.Type.FIXED), -1));
        localFileVoucherRepository.persistVouchers();
        localFileVoucherRepository.loadVouchers();
        assertTrue(localFileVoucherRepository.findById(voucher.getId()).isPresent());
    }
}
