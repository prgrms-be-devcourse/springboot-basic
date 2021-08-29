package org.prgrms.orderapp.repository;

import org.junit.jupiter.api.*;
import org.prgrms.orderapp.model.FixedAmountVoucher;
import org.prgrms.orderapp.model.PercentDiscountVoucher;
import org.prgrms.orderapp.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // PER_METHOD로 하면 테스트마다 리셋, 클래스로 하면 테스트 전체동안 동일
class FileVoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);

    private static final String PREFIX = "data";
    private static final String FILENAME = "storage-test.tmp";

    FileVoucherRepository voucherRepository = new FileVoucherRepository(PREFIX, FILENAME);

    private Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    private Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 30);

    @BeforeAll
    void init() {
        voucherRepository.loadStorage();
    }

    @Test
    @Order(1)
    @DisplayName("VoucherRepo should save voucher")
    public void testFileLoading() {
        Voucher retrievedVoucher = voucherRepository.save(voucher1);
        assertThat(voucherRepository.size(), is(1));
        assertThat(retrievedVoucher, samePropertyValuesAs(voucher1));
    }

    @Test
    @Order(2)
    @DisplayName("VoucherRepo should find the voucher by id")
    public void testFindById() {
        voucherRepository.save(voucher2);
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher2.getVoucherId());
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(voucher2));
    }

    @Test
    @Order(3)
    @DisplayName("VoucherRepo should return all vouchers")
    public void testFindAll() {
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList.size(), is(2));
        assertThat(voucherList, containsInAnyOrder(samePropertyValuesAs(voucher1), samePropertyValuesAs(voucher2)));
    }
}