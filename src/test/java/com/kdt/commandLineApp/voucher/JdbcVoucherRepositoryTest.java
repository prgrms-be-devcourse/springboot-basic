package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
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
    void deleteAllVoucher() {
        settingVoucher("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698", "fixed", 1000);

        var result1 = jdbcVoucherRepository.getAll().size();
        jdbcVoucherRepository.deleteAll();
        var result2 = jdbcVoucherRepository.getAll().size();

        assertThat(result1, is(1));
        assertThat(result2, is(0));
    }
}