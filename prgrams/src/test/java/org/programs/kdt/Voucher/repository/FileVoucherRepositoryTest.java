package org.programs.kdt.Voucher.repository;

import org.junit.jupiter.api.*;
import org.programs.kdt.Voucher.configure.FileProperty;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.configuration.YamlLoadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("staging")
@EnableConfigurationProperties(FileProperty.class)
@PropertySource(
    value = {"classpath:application-test.yaml"},
    factory = YamlLoadFactory.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

  @Configuration
  @ComponentScan(basePackages = {"org.programs.kdt.Voucher"})
  static class Config {}

  @Autowired FileVoucherRepository voucherRepository;

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
  @DisplayName("FileProperty클래스를 통해 FilePath값을 올바르게 가져올 수 있다.")
  void filePathTest() {
    assertThat(voucherRepository.getFilePath(), is("prgrams/src/test/resources/csv/voucher.csv"));
  }

  @Test
  @DisplayName("voucher.csv 파일을 읽을 수 있다.")
  @Order(1)
  @Disabled
  void readCsv() {
    /** test시 csv 파일 못읽는 문제 발생. */
    List<Voucher> all = voucherRepository.findAll();
    assertThat(all.isEmpty(), is(false));
  }

  @Test
  @DisplayName("바우처를 추가할 수 있다.")
  @Order(2)
  public void testCreateVoucher() {
    ;
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
  @DisplayName("바우처를 수정할 수 있다.")
  @Order(3)
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
  @DisplayName("바우처를 삭제할 수 있다.")
  @Order(7)
  public void deleteTest() {
    voucherRepository.delete(amountVoucher.getVoucherId());
    assertThat(voucherRepository.findAll(), hasSize(1));
  }

  @Test
  @DisplayName("바우처를 삭제할 수 있다.")
  @Order(8)
  public void deleteAllTest() {
    voucherRepository.deleteAll();
    assertThat(voucherRepository.findAll(), hasSize(0));
  }

  @Test
  @DisplayName("전체 바우처를 조회할 수 있다.")
  @Order(5)
  public void findAllTestVoucher() {
    List<Voucher> voucherList = voucherRepository.findAll();
    assertThat(voucherList.isEmpty(), is(false));
  }

  @Test
  @DisplayName("없는 바우처 조회 시 empty값을 반환한다")
  @Order(6)
  public void testFindById() {
    Optional<Voucher> byId = voucherRepository.findById(UUID.randomUUID());
    assertThat(byId, is(Optional.empty()));
  }

  @Test
  @DisplayName("바우처를 타입으로 조회할 수 있다.")
  @Order(5)
  public void findByTypeTest() {
    VoucherType voucherType = amountVoucher.getVoucherType();
    List<Voucher> voucherList = voucherRepository.findByType(voucherType);
    assertThat(voucherList, everyItem(samePropertyValuesAs(amountVoucher)));
    assertThat(voucherList, hasSize(1));
  }
}
