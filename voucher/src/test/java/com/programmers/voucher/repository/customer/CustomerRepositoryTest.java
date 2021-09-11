package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    VoucherRepository jdbcVoucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearDB() {
        jdbcTemplate.execute("TRUNCATE vouchers");
        jdbcTemplate.execute("TRUNCATE customers");
    }

    @Test
    @DisplayName("Load, Persist Vouchers Test")
    void loadAndPersist() {
        assertDoesNotThrow(() -> customerRepository.loadCustomers());
        assertDoesNotThrow(() -> customerRepository.persistCustomers());
    }

    @Test
    @DisplayName("Customer Creation Test")
    void createCustomer() {
        assertTrue(customerRepository.listAll().isEmpty());

        String username = "customer1";
        String alias = "alias-of-customer1";
        final Customer customer = customerRepository.save(new Customer(username, alias));
        assertEquals(customer.getUsername(), username);
        assertEquals(customer.getAlias(), alias);
    }

    @Test
    @DisplayName("Customer Read by ID Test")
    void readCustomerByID() {
        String username = "username1";
        String alias = "alias1";
        final Customer save = customerRepository.save(new Customer(username, alias));

        final Optional<Customer> byId = customerRepository.findById(save.getId());
        assertTrue(byId.isPresent());
        assertEquals(username, byId.get().getUsername());
        assertEquals(alias, byId.get().getAlias());
    }

    @Test
    @DisplayName("Customer Read by Voucher Test")
    void readCustomerByVoucher() {
        String username = "username2";
        String alias = "alias2";
        final Customer customer = customerRepository.save(new Customer(username, alias));
        Voucher voucher = jdbcVoucherRepository.save(new Voucher("voucher1", new DiscountPolicy(2500, DiscountType.FIXED), customer.getId()));

        Optional<Customer> byVoucher = customerRepository.findByVoucher(voucher.getId());
        assertTrue(byVoucher.isPresent());
        assertEquals(customer.getId(), byVoucher.get().getId());
    }

}
