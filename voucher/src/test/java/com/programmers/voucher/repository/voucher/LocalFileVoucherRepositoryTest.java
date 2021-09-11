package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Test
    @DisplayName("Load, Persist Vouchers Test")
    void loadAndPersist() {
        assertDoesNotThrow(() -> localFileVoucherRepository.loadVouchers());
        assertDoesNotThrow(() -> localFileVoucherRepository.persistVouchers());
    }

    @BeforeEach
    void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of("storage/test", "vouchers.db"));
        Files.createFile(Path.of("storage/test", "vouchers.db"));
        localFileVoucherRepository.loadVouchers();
    }

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);

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
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = localFileVoucherRepository.save(new Voucher(voucherName, discountPolicy, -12345));

        final Optional<Voucher> byId = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucherName, byId.get().getName());
    }

    @Test
    @DisplayName("Vouchers Read Test")
    void readVouchers() {
        Voucher voucher1 = localFileVoucherRepository.save(new Voucher("voucher1", new DiscountPolicy(2500, DiscountType.FIXED), 1111));
        Voucher voucher2 = localFileVoucherRepository.save(new Voucher("voucher2", new DiscountPolicy(2500, DiscountType.FIXED), 1111));
        Voucher voucher3 = localFileVoucherRepository.save(new Voucher("voucher3", new DiscountPolicy(2500, DiscountType.FIXED), 2222));
        Voucher voucher4 = localFileVoucherRepository.save(new Voucher("voucher4", new DiscountPolicy(2500, DiscountType.FIXED), 1111));

        List<Voucher> vouchers = localFileVoucherRepository.findAllByCustomer(1111);
        assertEquals(3, vouchers.size());
        assertEquals(voucher1, vouchers.get(0));
        assertEquals(voucher2, vouchers.get(1));
        assertNotEquals(voucher3, vouchers.get(2));
        assertEquals(voucher4, vouchers.get(2));
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = localFileVoucherRepository.save(new Voucher(voucherName, discountPolicy, -1));

        discountPolicy.updateAmount(5000);
        localFileVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.updateType(DiscountType.PERCENTAGE);
        localFileVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = localFileVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountType.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Voucher voucher = localFileVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountType.FIXED), -1));
        localFileVoucherRepository.deleteById(voucher.getId());
        assertTrue(localFileVoucherRepository.findById(voucher.getId()).isEmpty());
    }

    @Test
    @DisplayName("Local File Input/Output Test")
    void fileIO() {
        final Voucher voucher = localFileVoucherRepository.save(new Voucher("fileIO", new DiscountPolicy(1000, DiscountType.FIXED), -1));
        localFileVoucherRepository.persistVouchers();
        localFileVoucherRepository.loadVouchers();
        assertTrue(localFileVoucherRepository.findById(voucher.getId()).isPresent());
    }

    @Test
    @DisplayName("List All Vouchers Test")
    void listAll() {
        localFileVoucherRepository.listAll().stream().map(Voucher::getId).forEach(localFileVoucherRepository::deleteById);
        Voucher voucher1 = localFileVoucherRepository.save(new Voucher("voucher1", new DiscountPolicy(2500, DiscountType.FIXED), 1234));
        Voucher voucher2 = localFileVoucherRepository.save(new Voucher("voucher2", new DiscountPolicy(2500, DiscountType.FIXED), 1234));
        Voucher voucher3 = localFileVoucherRepository.save(new Voucher("voucher3", new DiscountPolicy(2500, DiscountType.FIXED), 1234));
        Voucher voucher4 = localFileVoucherRepository.save(new Voucher("voucher4", new DiscountPolicy(2500, DiscountType.FIXED), 1234));

        List<Voucher> vouchers = localFileVoucherRepository.listAll();
        assertEquals(4, vouchers.size());
        assertEquals(voucher1, vouchers.get(0));
        assertEquals(voucher2, vouchers.get(1));
        assertEquals(voucher3, vouchers.get(2));
        assertEquals(voucher4, vouchers.get(3));
    }
}
