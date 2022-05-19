package org.prgrms.vouchermanagement.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Sql("/schema.sql")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcVoucherRepositoryWithJdbcTestAnnotationTest {

  @Mock
  VoucherRepository voucherRepository;

  static Voucher fixedAmountVoucher1 = VoucherFactory.createVoucher(1, 100L, LocalDateTime.now());

  @BeforeEach
  void beforeEach() {
    voucherRepository.deleteAll();
  }

  @Test
  @Transactional
  @DisplayName("voucherJdbcRepository에 Voucher 인스턴스를 저장할 수 있다")
  void testInsert() {
    voucherRepository.insert(fixedAmountVoucher1);
//    assertThat(voucherRepository.count(), is(1));
  }
}

