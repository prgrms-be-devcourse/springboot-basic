package org.prgrms.kdtspringdemo.voucher.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
class FileVoucherRepositoryTest {

    @Configuration
    @ComponentScan()
    static class Config {
    }
    @Autowired
    private FileVoucherRepository fileVoucherRepository;
    private String filePath = "src/main/resources/csvFiles/voucherList.csv";
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);


    @BeforeEach
    void init() throws IOException {
        try(FileWriter fileWriter = new FileWriter(filePath);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("voucherId", "amount", "voucherType"));) {
            csvPrinter.printRecord(UUID.randomUUID().toString(), 100, "fixeddiscount");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @DisplayName("파일에 바우처 등록")
    void insert() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(20);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);

        //when
        Voucher insertVoucher = fileVoucherRepository.insert(voucher);

        //then
        assertThat(insertVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("voucherId로 바우처 검색")
    void findById() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(30);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);

        //when
        fileVoucherRepository.insert(voucher);
        Voucher findVoucher = fileVoucherRepository.findById(voucher.getVoucherId()).orElse(null);

        //then
        assertThat(findVoucher.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("모든 바우처 목록")
    void getAllVouchers() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(30);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);
        fileVoucherRepository.insert(voucher);

        //when
        List<Voucher> voucherMap = fileVoucherRepository.findAll().get();

        //then
        assertThat(voucherMap.size(), is(2));
    }
}