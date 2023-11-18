package com.programmers.vouchermanagement.voucher;

import com.programmers.vouchermanagement.VoucherManagementApplication;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.mapper.VoucherPolicyMapper;
import com.programmers.vouchermanagement.voucher.repository.JdbcVoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { VoucherManagementApplication.class },
        initializers = ConfigDataApplicationContextInitializer.class)
public class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository voucherRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clear() {
        jdbcTemplate.update("DELETE FROM voucher");
    }

    @Test
    @DisplayName("Voucher를 저장할 수 있다.")
    void successSave() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());

        // when
        voucherRepository.save(voucher);

        // then
        Voucher savedVoucher = voucherRepository.findById(voucher.getVoucherId()).get();
        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(savedVoucher.getVoucherPolicy().getDiscount()).isEqualTo(voucher.getVoucherPolicy().getDiscount());
        assertThat(savedVoucher.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(savedVoucher.getVoucherPolicy().getClass()).isEqualTo(voucher.getVoucherPolicy().getClass());
//        assertThat(savedVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }

    @Test
    @DisplayName("모든 Voucher를 조회할 수 있다.")
    void successFindAll() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT), LocalDateTime.now());
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(500L, VoucherType.FIXED), LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        List<Voucher> vouchers = voucherRepository.findAll();

        // then
        assertThat(vouchers).hasSize(3)
                .extracting(Voucher::getVoucherId)
                        .contains(voucher1.getVoucherId(), voucher2.getVoucherId(), voucher3.getVoucherId());
    }

    @Test
    @DisplayName("Voucher를 수정할 수 있다.")
    void successUpdate() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());
        voucherRepository.save(voucher);

        // when
        Voucher newVoucher = new Voucher(voucher.getVoucherId(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT), LocalDateTime.now());
        voucherRepository.update(newVoucher);

        // then
        Voucher updatedVoucher = voucherRepository.findById(voucher.getVoucherId()).get();
        assertThat(updatedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(updatedVoucher.getVoucherPolicy().getDiscount()).isEqualTo(newVoucher.getVoucherPolicy().getDiscount());
        assertThat(updatedVoucher.getVoucherType()).isEqualTo(newVoucher.getVoucherType());
        assertThat(updatedVoucher.getVoucherPolicy().getClass()).isEqualTo(newVoucher.getVoucherPolicy().getClass());
    }

    @Test
    @DisplayName("모든 Voucher를 삭제할 수 있다.")
    void successDeleteAll() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT), LocalDateTime.now());
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(500L, VoucherType.FIXED), LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        voucherRepository.deleteAll();

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(0);
    }

    @Test
    @DisplayName("올바르지 않은 아이디는 Voucher 조회 시 빈 Optional 객체를 생성한다.")
    void failFindById() {

        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());
        voucherRepository.save(voucher);

        // when
        Optional<Voucher> savedVoucher = voucherRepository.findById(UUID.randomUUID());

        // then
        assertThat(savedVoucher.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("아이디로 Voucher를 삭제할 수 있다.")
    void successDeleteById() {

        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(10000L, VoucherType.FIXED), LocalDateTime.now());
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, VoucherPolicyMapper.toEntity(80L, VoucherType.PERCENT), LocalDateTime.now());
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, VoucherPolicyMapper.toEntity(500L, VoucherType.FIXED), LocalDateTime.now());
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // when
        voucherRepository.deleteById(voucher1.getVoucherId());

        // then
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers).hasSize(2)
                .extracting(Voucher::getVoucherId)
                .contains(voucher2.getVoucherId(), voucher3.getVoucherId());
    }
}
