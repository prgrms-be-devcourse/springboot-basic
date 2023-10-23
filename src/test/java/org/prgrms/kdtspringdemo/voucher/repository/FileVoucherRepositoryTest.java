package org.prgrms.kdtspringdemo.voucher.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileVoucherRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }
    @Autowired
    private FileVoucherRepository fileVoucherRepository;
    private final String filePath = "src/test/resources/test_voucherList";
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);


    @BeforeAll
    void init() throws IOException {
        fileVoucherRepository.initCsvFileHandler(filePath);
        try(FileWriter fileWriter = new FileWriter(filePath);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("voucherId", "amount", "voucherType"));) {
            csvPrinter.printRecord(UUID.randomUUID().toString(), 100, "fixedAmount");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @Order(1)
    @DisplayName("파일에 바우처 등록")
    void insert() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 20, "percentDiscount");

        //when
        Voucher insertVoucher = fileVoucherRepository.insert(voucher);

        //then
        assertThat(insertVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @Order(2)
    @DisplayName("voucherId로 바우처 검색")
    void findById() {
        //given
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 30, "percentDiscount");

        //when
        fileVoucherRepository.insert(voucher);
        Voucher findVoucher = fileVoucherRepository.findById(voucher.getVoucherId()).orElse(null);

        //then
        assertThat(findVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @Order(3)
    @DisplayName("모든 바우처 목록")
    void getAllVouchers() {
        //when
        Map<UUID, Voucher> voucherMap = fileVoucherRepository.findAll().get();

        //then
        assertThat(voucherMap.size(), is(3));
    }
}