package org.programs.kdt.Voucher.repository;

import org.junit.jupiter.api.*;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("db")
class JdbcVoucherRepositoryTest {
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
          .addScript("voucher.sql")
          .build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

    @Bean
    JdbcVoucherRepository voucherRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcVoucherRepository(jdbcTemplate);
    }
  }

  @Autowired JdbcVoucherRepository voucherRepository;

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

  @Test
  @DisplayName("바우처를 추가할 수 있다.")
  @Order(1)
  public void testCreateVoucher() {
    voucherRepository.insert(percentVoucher);
    voucherRepository.insert(amountVoucher);

    Optional<Voucher> retrievePercentVoucher =
        voucherRepository.findById(percentVoucher.getVoucherId());

    assertThat(retrievePercentVoucher.isEmpty(), is(false));
    assertThat(retrievePercentVoucher.get(), samePropertyValuesAs(percentVoucher));

    Optional<Voucher> retrieveAmountVoucher =
        voucherRepository.findById(amountVoucher.getVoucherId());

    assertThat(retrieveAmountVoucher.isEmpty(), is(false));
    assertThat(retrieveAmountVoucher.get(), samePropertyValuesAs(amountVoucher));
  }

  @Test
  @DisplayName("같은 uuid를 가진 바우처를 추가시 에러가 발생한다")
  @Order(2)
  public void testDuplicationCreateVoucher() {
    assertThrows(DuplicateKeyException.class, () -> voucherRepository.insert(percentVoucher));
  }

  @Test
  @DisplayName("바우처를 수정할 수 있다.")
  @Order(5)
  public void testUpdateVoucher() {
    amountVoucher.changeValue(2000L);
    percentVoucher.changeValue(10L);
    voucherRepository.update(amountVoucher);
    voucherRepository.update(percentVoucher);

    Voucher retrieveAmountVoucher = voucherRepository.findById(amountVoucher.getVoucherId()).get();
    Voucher retrievePercentVoucher =
        voucherRepository.findById(percentVoucher.getVoucherId()).get();

    assertThat(retrievePercentVoucher.getValue(), is(percentVoucher.getValue()));
    assertThat(retrieveAmountVoucher.getValue(), is(amountVoucher.getValue()));

    assertThat(retrieveAmountVoucher.getValue(), is(2000L));
    assertThat(retrievePercentVoucher.getValue(), is(10L));
  }

  @Test
  @DisplayName("없는 바우처는 수정할 수 없다")
  @Order(6)
  public void testUpdateVoucherError() {

    VoucherType voucherType = VoucherType.PERCENT;
    Voucher newVoucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 40L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThrows(EntityNotFoundException.class, () -> voucherRepository.update(newVoucher));
  }

  @Test
  @DisplayName("바우처를 삭제 할 수 있다.")
  @Order(8)
  public void deleteTest() {
    voucherRepository.delete(amountVoucher.getVoucherId());
    assertThat(voucherRepository.findAll(), hasSize(1));
  }

  @Test
  @DisplayName("바우처를 전부 삭제할 수 있다.")
  @Order(9)
  public void deleteAllTest() {
    voucherRepository.deleteAll();
    assertThat(voucherRepository.findAll(), hasSize(0));
  }

  @Test
  @DisplayName("없는 바우처는 삭제 할 수 없다")
  @Order(10)
  public void deleteErrorTest() {
    VoucherType voucherType = VoucherType.PERCENT;
    Voucher voucher =
        voucherType.createVoucher(
            UUID.randomUUID(), 40L, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    assertThrows(
        EntityNotFoundException.class, () -> voucherRepository.delete(voucher.getVoucherId()));
  }

  @Test
  @DisplayName("전체 바우처를 조회할 수 있다.")
  @Order(3)
  public void findAllTestVoucher() {
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList.isEmpty(), is(false));
  }

  @Test
  @DisplayName("없는 바우처 조회 시 empty값을 반환한다")
  @Order(4)
  public void testFindById() {
    Optional<Voucher> byId = voucherRepository.findById(UUID.randomUUID());
    assertThat(byId, is(Optional.empty()));
  }

  @Test
  @DisplayName("바우처를 타입으로 조회할 수 있다.")
  @Order(7)
  public void findByTypeTest() {
    VoucherType voucherType = amountVoucher.getVoucherType();
    List<Voucher> voucherList = voucherRepository.findByType(voucherType);
    assertThat(voucherList, everyItem(samePropertyValuesAs(amountVoucher)));
    assertThat(voucherList, hasSize(1));
  }
}
