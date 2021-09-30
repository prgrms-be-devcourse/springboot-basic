package org.prgrms.kdt.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.exception.VoucherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("local")
class FileVoucherRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher.repository"}
    )
    static class Config{
    }

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    FileVoucherRepositoryProperties properties;

    Voucher newVoucher;

    String filePath;

    @BeforeAll
    void setUp(){
       newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
       filePath = new StringBuilder().append(properties.getDirectory())
                                .append(properties.getFileName())
                                .append(properties.getExtension())
                                .toString();
    }

    @AfterEach()
    void afterEach() throws IOException {
        Files.deleteIfExists(new File(filePath).toPath());
    }

    @Test
    @DisplayName("Voucher를 등록한다.")
    void testInsert(){
        //given //when
        Voucher insertResult = voucherRepository.insert(newVoucher);

        //then
        assertThat(insertResult, samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("파일이 없는 상태에서 Voucher를 조회하면 오류가 발생한다.")
    void testFindIfFileNotExist(){
        //given //when //then
        assertThrows(VoucherNotFoundException.class, ()->voucherRepository.findById(newVoucher.getVoucherId()));
        assertThrows(VoucherNotFoundException.class, ()->voucherRepository.findAllVouchers());
    }

    @Test
    @DisplayName("id에 해당하는 Voucher 객체를 반환한다.")
    void testFindById(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        Optional<Voucher> findResult = voucherRepository.findById(newVoucher.getVoucherId());

        //then
        assertThat(findResult.isEmpty(), is(false));
        assertThat(findResult.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("존재하지 않는 id에 대한 Voucher를 찾는다면 Empty 값을 반환한다.")
    void testFindByIdNotExist(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        Optional<Voucher> findResult = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(findResult.isEmpty(), is(true));
    }

    @Test
    @DisplayName("등록된 모든 Voucher를 반환한다.")
    void testFindAllVouchers(){
        //given
        Voucher newPercentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        voucherRepository.insert(newVoucher);
        voucherRepository.insert(newPercentVoucher);

        //when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        //then
        assertThat(allVouchers.size(), is(2));
        assertThat(allVouchers, hasItem(samePropertyValuesAs(newVoucher)));
        assertThat(allVouchers, hasItem(samePropertyValuesAs(newPercentVoucher)));
    }


    @Test
    @DisplayName("모든 Voucher를 삭제한다.")
    void testClearAllVouchers(){
        //given
        voucherRepository.insert(newVoucher);

        //when
        voucherRepository.clearAllVouchers();

        //then
        assertThat(voucherRepository.findAllVouchers().size(), is(0));
    }
}