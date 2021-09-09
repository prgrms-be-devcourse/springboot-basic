package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

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
        jdbcTemplate.execute("DELETE FROM customers");
        jdbcTemplate.execute("DELETE FROM vouchers");
    }

    @Test
    @DisplayName("Voucher Creation Test")
    void createVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));

        String voucherName = "voucher1";
        int price = 10000;
        DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);

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
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher(voucherName, discountPolicy, customer.getId()));

        final Optional<Voucher> byId = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(byId.isPresent());
        assertEquals(voucherName, byId.get().getName());

        final List<Voucher> allByCustomer = jdbcVoucherRepository.findAllByCustomer(customer.getId());
        assertEquals(1, allByCustomer.size());
        assertEquals(byId.get(), allByCustomer.get(0));
    }

    @Test
    @DisplayName("Voucher Update Test")
    void updateVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));

        String voucherName = "voucherUpdateMe";
        final DiscountPolicy discountPolicy = new DiscountPolicy(2500, DiscountPolicy.Type.FIXED);
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher(voucherName, discountPolicy, customer.getId()));

        discountPolicy.updateAmount(5000);
        jdbcVoucherRepository.update(voucher);

        final Optional<Voucher> updatedAmount = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(updatedAmount.isPresent());
        assertEquals(5000, updatedAmount.get().getDiscountPolicy().getAmount());

        discountPolicy.updateType(DiscountPolicy.Type.PERCENTAGE);
        jdbcVoucherRepository.update(voucher);

        final Optional<Voucher> updatedType = jdbcVoucherRepository.findById(voucher.getId());
        assertTrue(updatedType.isPresent());
        assertEquals(DiscountPolicy.Type.PERCENTAGE, updatedType.get().getDiscountPolicy().getType());
    }

    @Test
    @DisplayName("Voucher Deletion Test")
    void deleteVoucher() {
        final Customer customer = customerRepository.save(new Customer("username", "alias"));
        final Voucher voucher = jdbcVoucherRepository.save(new Voucher("voucher", new DiscountPolicy(2500, DiscountPolicy.Type.FIXED), customer.getId()));
        jdbcVoucherRepository.deleteById(voucher.getId());
        assertTrue(jdbcVoucherRepository.findById(voucher.getId()).isEmpty());
    }
    
}
