package com.kdt.commandLineApp;

import com.kdt.commandLineApp.service.customer.Customer;
import com.kdt.commandLineApp.service.customer.JdbcCustomerRepository;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import com.kdt.commandLineApp.service.voucher.JdbcVoucherRepository;
import com.kdt.commandLineApp.service.voucher.Voucher;
import com.kdt.commandLineApp.service.voucherWallet.JdbcVoucherWalletRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("db")
class JdbcVoucherWalletRepositoryTest {
    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherWalletRepository jdbcVoucherWalletRepository;

    void settingCustomer(String name, String age, String sex) {
        try {
            jdbcCustomerRepository.deleteAll();
            Customer customer = new Customer(name, age, sex);
            jdbcCustomerRepository.add(customer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void settingVoucher(String type, int amount) {
        try {
            jdbcVoucherRepository.deleteAll();
            Voucher voucher = new Voucher(type, amount);
            jdbcVoucherRepository.add(voucher);
        }
        catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void giveVoucherToCustomer() {
        settingCustomer("moon","20", "woman");
        settingVoucher("fixed",1000);
        long customerId = jdbcCustomerRepository.getAll().get(0).getId();
        long voucherId = jdbcVoucherRepository.getAll(0, 10, null).get(0).getId();

        jdbcVoucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);

        Customer result = jdbcVoucherWalletRepository.getCustomersWithVoucherId(voucherId).get(0);

        assertThat(result, isA(Customer.class));
        assertThat(result.getName(), Matchers.is("moon"));
        assertThat(result.getAge(), Matchers.is(20));
        assertThat(result.getSex(), Matchers.is("woman"));
    }

    @Test
    void deleteVoucherFromCustomer() {
        settingCustomer("moon","20", "woman");
        settingVoucher("fixed",1000);

        long customerId = jdbcCustomerRepository.getAll().get(0).getId();
        long voucherId = jdbcVoucherRepository.getAll(0, 10, null).get(0).getId();

        jdbcVoucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);
        int result1 = jdbcVoucherWalletRepository.getCustomerVouchers(customerId).size();

        jdbcVoucherWalletRepository.deleteVoucherFromCustomer(customerId, voucherId);
        int result2 = jdbcVoucherWalletRepository.getCustomerVouchers(customerId).size();

        assertThat(result1, Matchers.is(1));
        assertThat(result2, Matchers.is(0));
    }

    @Test
    void getCustomerVouchers() {
        settingCustomer("moon","20", "woman");
        settingVoucher("fixed",1000);

        long customerId = jdbcCustomerRepository.getAll().get(0).getId();
        long voucherId = jdbcVoucherRepository.getAll(0, 10, null).get(0).getId();

        jdbcVoucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);
        Voucher result = jdbcVoucherWalletRepository.getCustomerVouchers(customerId).get(0);

        assertThat(result.getId(), Matchers.is(voucherId));
        assertThat(result.getType(), Matchers.is("fixed"));
        assertThat(result.getDiscountAmount(), Matchers.is(1000));
    }

    @Test
    void getCustomersWithVoucherId() {
        settingCustomer("moon","20", "woman");
        settingVoucher("fixed",1000);

        long customerId = jdbcCustomerRepository.getAll().get(0).getId();
        long voucherId = jdbcVoucherRepository.getAll(0, 10, null).get(0).getId();

        jdbcVoucherWalletRepository.giveVoucherToCustomer(customerId, voucherId);

        Customer result = jdbcVoucherWalletRepository.getCustomersWithVoucherId(voucherId).get(0);

        assertThat(result.getId(), is(customerId));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }
}