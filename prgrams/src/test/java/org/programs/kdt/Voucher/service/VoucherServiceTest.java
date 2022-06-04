package org.programs.kdt.Voucher.service;

import org.junit.jupiter.api.*;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.repository.JdbcVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherServiceTest {

  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Voucher"})
  static class Config {
    @Bean
    public DataSource dataSource() {
      return new EmbeddedDatabaseBuilder()
          .generateUniqueName(true)
          .setType(H2)
          .setScriptEncoding("UTF-8")
          .ignoreFailedDrops(true)
          .addScript("customers.sql")
          .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

    @Bean
    JdbcVoucherRepository jdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcVoucherRepository(jdbcTemplate);
    }
  }

  Voucher percentVoucher;
  Voucher amountVoucher;

  @BeforeAll
  void setup() {
    VoucherType voucherType = VoucherType.PERCENT;
    percentVoucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 40L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    voucherType = VoucherType.FIXEDAMOUNT;
    amountVoucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 5000L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
  }

  @Autowired private VoucherService voucherService;

  @Test
  @DisplayName("바우처를 저장할 수 있다.")
  @Order(1)
  public void saveTest() {
    voucherService.insert(amountVoucher);
    voucherService.insert(percentVoucher);
    List<Voucher> voucherList = voucherService.findAll();
    assertThat(voucherList, hasSize(2));
    assertThat(voucherList, hasItem(amountVoucher));
    assertThat(voucherList, hasItem(percentVoucher));
  }

  @Test
  @DisplayName("똑같은 uuid를 가진 voucher는 생성할 수 없다.")
  @Order(2)
  public void saveTestError() {
    assertThrows(DuplicationException.class, () -> voucherService.insert(amountVoucher));
    assertThrows(DuplicationException.class, () -> voucherService.insert(percentVoucher));
  }

  @Test
  @DisplayName("uuid로 바우처를 삭제할 수 있다.")
  @Order(3)
  public void deleteTest() {
    voucherService.delete(amountVoucher.getVoucherId());
    assertThat(voucherService.findAll(), hasSize(1));
  }

  @Test
  @DisplayName("없는 UUID로 바우처를 삭제할 수 없다.")
  @Order(4)
  public void deleteTestError() {
    VoucherType voucherType = VoucherType.PERCENT;
    Voucher saveDeleteTest =
        voucherType.createVoucher(
            UUID.randomUUID(), 40L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThrows(
        EntityNotFoundException.class, () -> voucherService.delete(saveDeleteTest.getVoucherId()));
  }
}
