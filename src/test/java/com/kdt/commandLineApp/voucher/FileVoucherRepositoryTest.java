package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {
    @Autowired
    ApplicationContext applicationContext;

    VoucherRepository voucherRepository;

    @Before
    void set() {
        voucherRepository = applicationContext.getBean("voucherRepository",VoucherRepository.class);
    }

    @Test
    void add() {
        try {
            voucherRepository.add(new Voucher("fixed",1000f));
            assertThat(voucherRepository.getAll().size(), is(1));
            voucherRepository.add(new Voucher("percent",10f));
            assertThat(voucherRepository.getAll().size(), is(2));
        } catch (Exception e) {
        }
    }
}