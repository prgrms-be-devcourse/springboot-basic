package prgms.spring_week1.domain.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import prgms.spring_week1.domain.voucher.repository.impl.JdbcVoucherRepository;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@JdbcTest
class VoucherServiceTest {
    private VoucherService voucherService;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setup(){
        voucherService = new VoucherService(new JdbcVoucherRepository(dataSource));
    }

    @Test
    void findAll() {
        assertThat(voucherService.findAll(),hasSize(2));
    }

    @Test
    void findByType() {
        assertThat(voucherService.findByType("PERCENT"),hasSize(2));
    }

    @Test
    void deleteAll() {
        voucherService.deleteAll();
        assertThat(voucherService.findAll(),hasSize(0));
    }
}
