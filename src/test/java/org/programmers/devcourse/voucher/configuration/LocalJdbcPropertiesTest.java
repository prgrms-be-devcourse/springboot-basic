package org.programmers.devcourse.voucher.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class LocalJdbcPropertiesTest {

  @Autowired
  private LocalJdbcProperties localJdbcProperties;


  @Test
  @DisplayName("세 속성은 전부 null 이 아니어야 한다.")
  void all_methods_should_not_return_null() {

    assertThat(localJdbcProperties.getUrl()).isNotBlank();
    assertThat(localJdbcProperties.getPassword()).isNotBlank();
    assertThat(localJdbcProperties.getUser()).isNotBlank();
  }


}
