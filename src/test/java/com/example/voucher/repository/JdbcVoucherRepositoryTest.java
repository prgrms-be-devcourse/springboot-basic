package com.example.voucher.repository;

import static org.springframework.test.util.AssertionErrors.*;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.Voucher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JdbcRepositoryConfig.class)
class JdbcVoucherRepositoryTest {

    public static String FAILED = "test failed";

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("바우처 객체가 있을 때 저장하면 디비에서 동일한 ID의 데이터를 객체로 반환한다.")
    @Test
    void save() {
        Voucher voucher = new FixedAmountVoucher(10L);
        UUID expectedId = voucher.getVoucherId();

        Voucher savedVoucher = jdbcVoucherRepository.save(voucher);
        UUID actualId = savedVoucher.getVoucherId();

        assertEquals(FAILED, expectedId, actualId);
    }

}
