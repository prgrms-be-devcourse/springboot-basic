package org.programmers.devcourse.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class VoucherApplicationTests {

  @Autowired
  VoucherRepository voucherRepository;

  @Test
  @DisplayName("VoucherRepository는 null이 아니어야 한다.")
  void contextLoads() {
    assertThat(voucherRepository).isNotNull();


  }


}
