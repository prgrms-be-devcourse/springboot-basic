package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import com.kdt.commandLineApp.voucher.JdbcVoucherRepository;
import com.kdt.commandLineApp.voucher.Voucher;
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
class JdbcCustomerRepositoryTest {
    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;


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
    void add() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464", "moon",25,"man");

        var result = jdbcCustomerRepository.get(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464")).get();

        assertThat(result, isA(Customer.class));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(25));
        assertThat(result.getSex(), is("man"));
    }

    @Test
    void getCustomersWithVoucherId() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);

        jdbcVoucherRepository.giveVoucherToCustomer(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464"), UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));

        var result = jdbcCustomerRepository.getCustomersWithVoucherId(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698")).get(0);

        assertThat(result.getCustomerId(), is(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464")));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }

    @Test
    void getAll() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");

        var result =  jdbcCustomerRepository.getAll().size();

        assertThat(result, is(1));
    }

    @Test
    void get() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");

        var result = jdbcCustomerRepository.get(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464")).get();

        assertThat(result.getCustomerId(), is(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464")));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }

}