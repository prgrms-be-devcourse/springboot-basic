package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.NotFoundVoucherException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


//test에서 에서 '@Transactional'을 사용시, 스프링은 테스트를 트랜잭션안에서 시행하고 끝나면 자동으로 rollback 시켜준다.
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("저장소에 voucher를 저장할 수 있다.")
    void insertTest() {
        Voucher fixedVoucher = voucherRepository.insert("1", 20L);
        Voucher percentVoucher = voucherRepository.insert("2", 50L);

        assertThat(fixedVoucher).isEqualTo(voucherRepository.findById(fixedVoucher.getVoucherId()));
        assertThat(percentVoucher).isEqualTo(voucherRepository.findById(percentVoucher.getVoucherId()));
    }

    @Test
    @DisplayName("저장소에서 모든 voucher 들을 가져올 수 있다.")
    void findAllTest() {
        Voucher fixedVoucher = voucherRepository.insert("1", 20L);
        Voucher percentVoucher = voucherRepository.insert("2", 50L);

        List<Voucher> results = voucherRepository.findAll();

        assertThat(results).hasSize(2);
        assertThat(results).contains(fixedVoucher, percentVoucher);
    }

    @Test
    @DisplayName("저장소에서 voucher를 수정할 수 있다.")
    void updateCorrectTest() {
        Voucher fixedVoucher = voucherRepository.insert("1", 20L);
        long voucherId = fixedVoucher.getVoucherId();

        voucherRepository.update(voucherId, 90L);

        assertThat(voucherRepository.findById(voucherId).getDiscountDegree()).isEqualTo(90L);
    }

    @Test
    @DisplayName("findById에서 잘못된 ID값을 넣어주면 예외가 발생한다.")
    void findByIdIncorrectTest() {
        assertThatThrownBy(() -> {
            voucherRepository.findById(999999L);
        }).isInstanceOf(NotFoundVoucherException.class);
    }

    @Test
    @DisplayName("update에서 잘못된 ID값을 넣어주면 에러가 발생한다.")
    void updateIncorrectTest() {

        assertThatThrownBy(() -> {
            voucherRepository.update(99L, 90L);
        }).isInstanceOf(NotFoundVoucherException.class);
    }
}
