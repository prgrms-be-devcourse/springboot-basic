package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;

    @Test
    void add() {
        try {
            Voucher voucher = new Voucher("fixed",1000);
            voucherRepository.add(voucher);

            var result = voucherRepository.getAll().size();

            assertThat(result, is(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}