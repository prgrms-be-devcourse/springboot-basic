package org.prgrms.kdtspringdemo.voucher.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
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

    @BeforeEach
    void init() {
        voucherService = new VoucherService(voucherRepository);
        voucherRepository.initCsvFileHandler(filePath);
        try(FileWriter fileWriter = new FileWriter(filePath);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("voucherId", "amount", "voucherType"));) {
            csvPrinter.printRecord(UUID.randomUUID().toString(), 100, "fixeddiscount");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
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
    @DisplayName("잘못된 바우처 타입이 입력되면 NoSuchElementException")
    void getVoucherTypeFunctionError() {
        //given
        String voucherType = "noneVoucher";

        //then
        assertThrows(NoSuchElementException.class, () -> voucherService.getVoucherTypeFunction(voucherType));
    }

    @Test
    @DisplayName("바우처를 생성합니다")
    void createVoucher() {
        //given
        VoucherTypeFunction voucherType = VoucherTypeFunction.PERCENT_DISCOUNT_POLICY;
        UUID voucherId = UUID.randomUUID();
        long amount = 20L;

        //when
        Voucher createdVoucher = voucherService.createVoucher(voucherType, voucherId, amount);

        //then
        assertThat(createdVoucher.getVoucherId(), is(voucherId));
        assertThat(createdVoucher.getVoucherPolicy(), instanceOf(PercentDiscountPolicy.class));
    }

    @Test
    @DisplayName("바우처 리스트를 반환합니다.")
    void getVoucherList() {
        //given
        voucherService.createVoucher(VoucherTypeFunction.PERCENT_DISCOUNT_POLICY, UUID.randomUUID(), 20L);
        voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, UUID.randomUUID(), 400L);

        //when
        List<Voucher> voucherList = voucherService.findAll().get();

        //then
        assertThat(voucherList.size(), is(3));
    }

    @Test
    @DisplayName("voucherId 로 바우처를 반환합니다.")
    void getVoucher() {
        //given
        Voucher createdVoucher = voucherService.createVoucher(VoucherTypeFunction.FIXED_DISCOUNT_POLICY, UUID.randomUUID(), 2000L);

        //when
        Voucher voucher = voucherService.findById(createdVoucher.getVoucherId());

        //then
        assertThat(createdVoucher.getVoucherId(), is(voucher.getVoucherId()));
        assertThat(createdVoucher.getVoucherPolicy().getClass(), sameInstance(voucher.getVoucherPolicy().getClass()));
    }
}