package com.kdt.commandLineApp;

import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.customer.JdbcCustomerRepository;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import com.kdt.commandLineApp.voucher.JdbcVoucherRepository;
import com.kdt.commandLineApp.voucher.Voucher;
import com.kdt.commandLineApp.voucherWallet.JdbcVoucherWalletRepository;
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
    private String custmerId = "90876254-1988-4f45-b296-ebbb6fedd464";
    private String voucherId = "34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698";

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherWalletRepository jdbcVoucherWalletRepository;

    void settingCustomer(String uuid, String name, int age, String sex) {
        try {
            jdbcCustomerRepository.deleteAll();
            Customer customer = new Customer(UUID.fromString(uuid), name, age, sex);
            jdbcCustomerRepository.add(customer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void settingVoucher(String uuid, String type, int amount) {
        try {
            jdbcVoucherRepository.deleteAll();
            Voucher voucher = new Voucher(UUID.fromString(uuid), type, amount);
            jdbcVoucherRepository.add(voucher);
        }
        catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void giveVoucherToCustomer() {
        settingCustomer(custmerId,"moon",20, "woman");
        settingVoucher(voucherId,"fixed",1000);

        jdbcVoucherWalletRepository.giveVoucherToCustomer(custmerId, voucherId);

        Customer result = jdbcVoucherWalletRepository.getCustomersWithVoucherId(voucherId).get(0);

        assertThat(result, isA(Customer.class));
        assertThat(result.getName(), Matchers.is("moon"));
        assertThat(result.getAge(), Matchers.is(20));
        assertThat(result.getSex(), Matchers.is("woman"));
    }

    @Test
    void deleteVoucherFromCustomer() {
        settingCustomer(custmerId,"moon",20, "woman");
        settingVoucher(voucherId,"fixed",1000);

        jdbcVoucherWalletRepository.giveVoucherToCustomer(custmerId, voucherId);
        int result1 = jdbcVoucherWalletRepository.getCustomerVouchers(custmerId).size();

        jdbcVoucherWalletRepository.deleteVoucherFromCustomer(custmerId, voucherId);
        int result2 = jdbcVoucherWalletRepository.getCustomerVouchers(custmerId).size();

        assertThat(result1, Matchers.is(1));
        assertThat(result2, Matchers.is(0));
    }

    @Test
    void getCustomerVouchers() {
        settingCustomer(custmerId,"moon",20, "woman");
        settingVoucher(voucherId,"fixed",1000);

        jdbcVoucherWalletRepository.giveVoucherToCustomer(custmerId, voucherId);
        Voucher result = jdbcVoucherWalletRepository.getCustomerVouchers(custmerId).get(0);

        assertThat(result.getId(), Matchers.is(UUID.fromString(voucherId)));
        assertThat(result.getType(), Matchers.is("fixed"));
        assertThat(result.getDiscountAmount(), Matchers.is(1000));
    }

    @Test
    void getCustomersWithVoucherId() {
        settingCustomer(custmerId,"moon",20, "woman");
        settingVoucher(voucherId,"fixed",1000);

        jdbcVoucherWalletRepository.giveVoucherToCustomer(custmerId, voucherId);

        Customer result = jdbcVoucherWalletRepository.getCustomersWithVoucherId(voucherId).get(0);

        assertThat(result.getCustomerId(), is(UUID.fromString(custmerId)));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }
}