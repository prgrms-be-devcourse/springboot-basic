package org.programs.kdt.Voucher.configure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.programs.kdt.configuration.YamlLoadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig
@ActiveProfiles("staging")
@EnableConfigurationProperties(FileProperty.class)
@PropertySource(
    value = {"classpath:application-test.yaml"},
    factory = YamlLoadFactory.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilePropertyTest {
  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Voucher"})
  static class Config {}

  @Autowired FileProperty fileProperty;

  @Test
  @DisplayName("파일프로퍼티의 voucher 값을 읽을 수 있다.")
  void readVoucher() {
    assertThat(fileProperty.getVoucher(), is("prgrams/src/test/resources/csv/voucher.csv"));
  }

  @Test
  @DisplayName("파일프로퍼티의 blacklist 값을 읽을 수 있다.")
  void readBlacklist() {
    assertThat(fileProperty.getBlacklist(), is("prgrams/src/test/resources/csv/blacklist.csv"));
  }
}
