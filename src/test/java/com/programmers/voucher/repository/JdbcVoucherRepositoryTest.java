package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedDiscount;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan("com.programmers.voucher")
    static class Config{
    }

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    // 테스트 컨테이너 연결을 확인하는 테스트는 어떻게 짜면 좋을지 모르겠습니다!
    // 현재는 insert()가 정상적으로 작동했는지를 토대로 확인했습니다.
    @DisplayName("DB에 바우처 정보를 저장할 수 있다.")
    @Test
    void insertVoucherTest() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());

        Voucher storedVoucher = jdbcVoucherRepository.save(testVoucher);
        
        assertThat(testVoucher.getVoucherId()).isEqualTo(storedVoucher.getVoucherId());
    }

    @DisplayName("ID로 바우처를 조회할 수 있다.")
    @Test
    void findVoucherByIdTest() {
        UUID id = UUID.randomUUID();
        Voucher testVoucher = new Voucher(id, new FixedDiscount(100), LocalDateTime.now());
        jdbcVoucherRepository.save(testVoucher);

        Voucher storedVoucher = jdbcVoucherRepository.findById(id);

        assertThat(storedVoucher.getVoucherId()).isEqualTo(id);
    }

    @DisplayName("DB에서 모든 바우처를 조회할 수 있다.")
    @Test
    void findAllTest() {
        Voucher testVoucher = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());
        Voucher testVoucher2 = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());
        Voucher testVoucher3 = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());
        jdbcVoucherRepository.save(testVoucher);
        jdbcVoucherRepository.save(testVoucher2);
        jdbcVoucherRepository.save(testVoucher3);

        List<Voucher> vouchers = jdbcVoucherRepository.findAll();

        assertThat(vouchers.size()).isEqualTo(3);
    }
}
