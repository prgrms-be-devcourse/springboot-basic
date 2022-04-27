package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.service.voucher.JdbcVoucherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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

    @Test
    void add() {
        settingCustomer("moon","25","man");

        Customer result = jdbcCustomerRepository.getAll().get(0);

        assertThat(result, isA(Customer.class));
        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(25));
        assertThat(result.getSex(), is("man"));
    }

    @Test
    void getAll() {
        settingCustomer("moon","20", "woman");

        int result =  jdbcCustomerRepository.getAll().size();

        assertThat(result, is(1));
    }

    @Test
    void get() {
        settingCustomer("moon","20", "woman");

        Customer result = jdbcCustomerRepository.getAll().get(0);

        assertThat(result.getName(), is("moon"));
        assertThat(result.getAge(), is(20));
        assertThat(result.getSex(), is("woman"));
    }
}