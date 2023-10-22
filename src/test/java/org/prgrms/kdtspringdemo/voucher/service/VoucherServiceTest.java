package org.prgrms.kdtspringdemo.voucher.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.file.CsvFileHandler;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.FileVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
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
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }

    private VoucherService voucherService;
    private final FileVoucherRepository voucherRepository = new FileVoucherRepository();
    private final String filePath = "src/test/resources/test_voucherList";
    private final Logger logger = LoggerFactory.getLogger(VoucherServiceTest.class);

    @BeforeAll
    void init() {
        voucherService = new VoucherService(voucherRepository);
        voucherRepository.initCsvFileHandler(filePath);
        try(FileWriter fileWriter = new FileWriter(filePath);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("voucherId", "amount", "voucherType"));) {
            csvPrinter.printRecord(UUID.randomUUID().toString(), 100, "fixedAmount");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    @Order(1)
    @DisplayName("바우처 타입 이넘을 반환한다.")
    void getVoucherTypeFunction() {
        //given
        String voucherType = "fixedAmount";

        //when
        VoucherTypeFunction voucherTypeFunction = voucherService.getVoucherTypeFunction(voucherType);

        //then
        assertThat(voucherTypeFunction.name(), is("FIXED_AMOUNT_VOUCHER"));
    }

    @Test
    @Order(2)
    @DisplayName("잘못된 바우처 타입이 입력되면 NoSuchElementException")
    void getVoucherTypeFunctionError() {
        //given
        String voucherType = "noneVoucher";

        //then
        assertThrows(NoSuchElementException.class, () -> voucherService.getVoucherTypeFunction(voucherType));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 생성합니다")
    void createVoucher() {
        //given
        VoucherTypeFunction voucherType = VoucherTypeFunction.PERCENT_DISCOUNT_VOUCHER;
        UUID voucherId = UUID.randomUUID();
        long amount = 20L;

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucherType, voucherId, amount);

        //then
        assertThat(createdVoucher.getVoucherId(), is(voucherId));
        assertThat(createdVoucher, instanceOf(PercentDiscountVoucher.class));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 리스트를 반환합니다.")
    void getVoucherList() {
        //when
        Map<UUID, Voucher> voucherMap = voucherService.getVoucherList();

        //then
        assertThat(voucherMap.size(), is(2));
    }

    @Test
    @Order(5)
    @DisplayName("voucherId 로 바우처를 반환합니다.")
    void getVoucher() {
        //given
        Voucher createdVoucher = voucherService.createVoucher(VoucherTypeFunction.FIXED_AMOUNT_VOUCHER, UUID.randomUUID(), 2000L);

        //when
        Voucher voucher = voucherService.getVoucher(createdVoucher.getVoucherId());

        //then
        assertThat(createdVoucher, samePropertyValuesAs(voucher));
    }
}