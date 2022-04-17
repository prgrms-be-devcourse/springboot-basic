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

    @Test
    void add() {
        //@Before 아노테이션으로 적용한 deleteAll이 반영이 안되네요..
        jdbcVoucherRepository.deleteAll();
        Voucher voucher = null;
        UUID voucherID = UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698");
        try {
            voucher = new Voucher(voucherID, "fixed",1000);
        } catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }

        jdbcVoucherRepository.add(voucher);
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
        jdbcVoucherRepository.deleteAll();

        Voucher voucher = null;
        UUID voucherID = UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698");
        try {
            voucher = new Voucher(voucherID, "fixed",1000);
        } catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }
        jdbcVoucherRepository.add(voucher);

        jdbcVoucherRepository.remove(voucherID);
        int size = jdbcVoucherRepository.getAll().size();

        assertThat(size, is(0));
    }

    @Test
    void get() {
        jdbcVoucherRepository.deleteAll();

        Voucher voucher1 = null;
        UUID voucherID = UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698");

        try {
            voucher1 = new Voucher(voucherID, "fixed",1000);
        } catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }

        jdbcVoucherRepository.add(voucher1);
        Voucher voucher2 = jdbcVoucherRepository.get(voucherID).get();

        assertThat(voucher1, isA(Voucher.class));
        assertThat(voucher1.getId(), is(voucher2.getId()));
        assertThat(voucher1.getType(), is(voucher2.getType()));
        assertThat(voucher1.getDiscountAmount(), is(voucher2.getDiscountAmount()));
    }

    @Test
    void getAll() {
        jdbcVoucherRepository.deleteAll();

        Voucher voucher1 = null;
        UUID voucherID = UUID.fromString("34c20e5a-15e3-4c1a-a57d-5cd5e5cf3698");

        try {
            voucher1 = new Voucher(voucherID, "fixed",1000);
        } catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }

        jdbcVoucherRepository.add(voucher1);
        List<Voucher> voucherList = jdbcVoucherRepository.getAll();
        int size = voucherList.size();

        assertThat(size, is(1));
    }

    @Test
    void testAdd() {
    }

    @Test
    void testRemove() {
    }

    @Test
    void testGet() {
    }

    @Test
    void testGetAll() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void giveVoucherToCustomer() {
    }

    @Test
    void deleteVoucherFromCustomer() {
    }

    @Test
    void getVouchers() {
    }
}