package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.customer.Customer;
import com.kdt.commandLineApp.customer.JdbcCustomerRepository;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("db")
class JdbcVoucherRepositoryTest {
    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

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
            UUID voucherID = UUID.fromString(uuid);
            Voucher voucher = new Voucher(voucherID, type, amount);
            jdbcVoucherRepository.add(voucher);
        }
        catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }
    }

    @Test
    void add() {
        //@Before 아노테이션으로 적용한 deleteAll이 반영이 안되네요..
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);

        List<Voucher> voucherList = jdbcVoucherRepository.getAll();
        int size = voucherList.size();

        assertThat(size, is(1));
        assertThat(voucherList.get(0), isA(Voucher.class));
        assertThat(voucherList.get(0).getId().toString(), is("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));
        assertThat(voucherList.get(0).getType(), is("fixed"));
        assertThat(voucherList.get(0).getDiscountAmount(), is(1000));
    }

    @Test
    void remove() {
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);
        jdbcVoucherRepository.remove(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));
        int size = jdbcVoucherRepository.getAll().size();

        assertThat(size, is(0));
    }

    @Test
    void get() {
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);
        Voucher voucher = jdbcVoucherRepository.get(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698")).get();

        assertThat(voucher, isA(Voucher.class));
        assertThat(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"), is(voucher.getId()));
        assertThat("fixed", is(voucher.getType()));
        assertThat(1000, is(voucher.getDiscountAmount()));
    }

    @Test
    void getAll() {
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698", "fixed", 1000);

        var result = jdbcVoucherRepository.getAll().size();

        assertThat(result, is(1));
    }

    @Test
    void deleteAll() {
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698", "fixed", 1000);

        var result1 = jdbcVoucherRepository.getAll().size();
        jdbcVoucherRepository.deleteAll();
        var result2 = jdbcCustomerRepository.getAll().size();

        assertThat(result1, is(1));
        assertThat(result1, is(0));
    }

    @Test
    void giveVoucherToCustomer() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);

        jdbcVoucherRepository.giveVoucherToCustomer(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464"), UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));

        var result = jdbcCustomerRepository.getCustomersWithVoucherId(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698")).get(0);

        assertThat(result, isA(Customer.class));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }

    @Test
    void deleteVoucherFromCustomer() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);
        jdbcVoucherRepository.giveVoucherToCustomer(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464"), UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));

        jdbcVoucherRepository.deleteVoucherFromCustomer(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464"));
        var result = jdbcVoucherRepository.getAll().size();

        assertThat(result, is(0));
    }

    @Test
    void getCustomerVouchers() {
        settingCustomer("90876254-1988-4f45-b296-ebbb6fedd464","moon",20, "woman");
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698","fixed",1000);

        jdbcVoucherRepository.giveVoucherToCustomer(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464"), UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698"));
        var result = jdbcVoucherRepository.getCustomerVouchers(UUID.fromString("90876254-1988-4f45-b296-ebbb6fedd464")).get(0);

        assertThat(result.getId(), is(UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698")));
        assertThat(result.getType(), is("fixed"));
        assertThat(result.getDiscountAmount(), is(1000));
    }

    @Test
    void checkVoucherValidation() {

    }
}