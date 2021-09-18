package org.programmers.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.voucher.model.FixedDiscountVoucher;
import org.programmers.voucher.model.PercentDiscountVoucher;
import org.programmers.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherBaseRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherBaseRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.voucher.repository"})
    static class Config {
        @Bean
        FileMemoryVoucherRepository fileMemoryVoucherRepository() {
            return new FileVoucherRepository();
        }
    }

    @Autowired
    FileVoucherRepository fileVoucherRepository;

    Voucher fixedVoucher;
    Voucher percentVoucher;

    @BeforeAll
    void setUp() {
        fileVoucherRepository.loadVoucherList();
    }

    @BeforeEach
    void setUpEach() {
        fixedVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 10L);
        percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        fileVoucherRepository.save(fixedVoucher);
        fileVoucherRepository.save(percentVoucher);
    }

    @AfterAll
    void clearUpEach() {
        fileVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("아이디로 메모리에 기록된 바우처를 조회할 수 있다.")
    void findById() {
        Optional<Voucher> findFixedVoucherInterfaceById = fileVoucherRepository.findById(fixedVoucher.getVoucherId());
        Optional<Voucher> findPercentVoucherInterfaceById = fileVoucherRepository.findById(percentVoucher.getVoucherId());
        assertThat(findFixedVoucherInterfaceById.get(), samePropertyValuesAs(fixedVoucher));
        assertThat(findPercentVoucherInterfaceById.get(), samePropertyValuesAs(percentVoucher));
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAllVouchers() {
        List<Voucher> allVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(allVouchers, hasSize(2));
    }

    @Test
    @DisplayName("바우처를 저장할 수 있다.")
    void save() {
        Voucher fixedVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 20L);
        Voucher percentVoucherItnerface = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        fileVoucherRepository.save(fixedVoucher);
        fileVoucherRepository.save(percentVoucherItnerface);

        List<Voucher> allVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(allVouchers, hasSize(6));
    }

    @Test
    @DisplayName("메모리에 기록된 모든 바우처를 삭제할 수 있다.")
    void deleteAll() {
        List<Voucher> beforeVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(beforeVouchers, hasSize(4));
        fileVoucherRepository.deleteAll();

        List<Voucher> afterVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(afterVouchers, hasSize(0));
    }
}