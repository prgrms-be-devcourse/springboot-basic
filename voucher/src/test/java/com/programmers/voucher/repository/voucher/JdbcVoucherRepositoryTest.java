package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository jdbcVoucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("TRUNCATE customers");
        jdbcTemplate.execute("TRUNCATE vouchers");
    }

    @Test
    @DisplayName("Load, Persist Vouchers Test")
    void loadAndPersist() {
        assertDoesNotThrow(() -> jdbcVoucherRepository.loadVouchers());
        assertDoesNotThrow(() -> jdbcVoucherRepository.persistVouchers());
    }

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));

        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);

        final Voucher voucher = jdbcVoucherRepository.save(new Voucher(voucherName, discountPolicy, customer.getId()));
        assertEquals(voucherName, voucher.getName());
        assertNotNull(voucher.getCreatedAt());
        assertEquals(discountPolicy.getType(), voucher.getDiscountPolicy().getType());
        assertEquals(discountPolicy.getAmount(), voucher.getDiscountPolicy().getAmount());

        assertEquals(price - discountPolicy.getAmount(), discountPolicy.discount(price));
    }

    @Test
    @DisplayName("Voucher Read Test")
    void readVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));
        String voucherName = "voucherReadme";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher(voucherName, discountPolicy, customer.getId()));

        Optional<Voucher> byId = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucher, byId.get());
    }

    @Test
    @DisplayName("Vouchers Read Test")
    void readVouchers() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher1 = jdbcVoucherRepository.save(new Voucher("voucher1", discountPolicy, customer.getId()));
        final Voucher voucher2 = jdbcVoucherRepository.save(new Voucher("voucher2", discountPolicy, customer.getId()));
        final Voucher voucher3 = jdbcVoucherRepository.save(new Voucher("voucher3", discountPolicy, customer.getId()));
        final Voucher voucher4 = jdbcVoucherRepository.save(new Voucher("voucher4", discountPolicy, customer.getId()+1));
        final Voucher voucher5 = jdbcVoucherRepository.save(new Voucher("voucher5", discountPolicy, customer.getId()));

        final List<Voucher> allByCustomer = jdbcVoucherRepository.findAllByCustomer(customer.getId());
        assertEquals(4, allByCustomer.size());
        assertEquals(voucher1, allByCustomer.get(0));
        assertEquals(voucher2, allByCustomer.get(1));
        assertEquals(voucher3, allByCustomer.get(2));
        assertNotEquals(voucher4, allByCustomer.get(3));
        assertEquals(voucher5, allByCustomer.get(3));
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));

        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountType.FIXED);
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher(voucherName, discountPolicy, customer.getId()));

        discountPolicy.updateAmount(5000);
        jdbcVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.updateType(DiscountType.PERCENTAGE);
        jdbcVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountType.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountType.FIXED), customer.getId()));
        jdbcVoucherRepository.deleteById(voucher.getId());
        assertTrue(jdbcVoucherRepository.findById(voucher.getId()).isEmpty());
    }

    @Test
    @DisplayName("List All Vouchers")
    void listAll() {
        Voucher name1 = jdbcVoucherRepository.save(new Voucher(1L, "name1", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1234));
        Voucher name2 = jdbcVoucherRepository.save(new Voucher(2L, "name2", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1234));
        Voucher name3 = jdbcVoucherRepository.save(new Voucher(3L, "name3", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1234));
        Voucher name4 = jdbcVoucherRepository.save(new Voucher(4L, "name4", new DiscountPolicy(2500, DiscountType.FIXED), LocalDate.now(), 1234));

        List<Voucher> vouchers = jdbcVoucherRepository.listAll();
        assertEquals(4, vouchers.size());
        assertEquals(name1, vouchers.get(0));
        assertEquals(name2, vouchers.get(1));
        assertEquals(name3, vouchers.get(2));
        assertEquals(name4, vouchers.get(3));
    }
    
}
