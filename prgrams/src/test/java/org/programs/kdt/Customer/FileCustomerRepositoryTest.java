package org.programs.kdt.Customer;

import org.junit.jupiter.api.*;
import org.programs.kdt.Voucher.configure.FileProperty;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCustomerRepositoryTest {

  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Customer"})
  static class Config {}

  @Autowired FileCustomerRepository fileCustomerRepository;

  @Test
  @DisplayName("파일경로를 가져올 수 있다.")
  void filePathTest() {
    assertThat(fileCustomerRepository.getFilePath().isEmpty(), is(false));
  }
}
