package org.prgrms.vouchermanagement.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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

