package org.programmers.devcourse.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("dev")
class VoucherApplicationTests {

  @Autowired
  VoucherRepository voucherRepository;

  @Test
  void contextLoads() {
    assertThat(voucherRepository).isNotNull();


  }

  @Test
  @DisplayName("파일 기반 VoucherRepository에는 최소 1개 이상의 데이터가 존재해야 한다.")
  void fileBasedVoucherRepositoryIsNotEmpty() {

    var storage = voucherRepository.getAllVouchers();
    assertThat(storage).isNotEmpty();

  }

}
