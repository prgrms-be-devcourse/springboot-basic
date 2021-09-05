package org.programmers.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.voucher.model.FixedDiscountVoucherInterface;
import org.programmers.voucher.model.PercentDiscountVoucherInterface;
import org.programmers.voucher.model.VoucherInterface;
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
class FileVoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);

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

    VoucherInterface fixedVoucherInterface;
    VoucherInterface percentVoucherInterface;

    @BeforeAll
    void setUp() {
        fileVoucherRepository.loadVoucherList();
    }

    @BeforeEach
    void setUpEach() {
        fixedVoucherInterface = new FixedDiscountVoucherInterface(UUID.randomUUID(), 10L);
        percentVoucherInterface = new PercentDiscountVoucherInterface(UUID.randomUUID(), 10L);

        fileVoucherRepository.save(fixedVoucherInterface);
        fileVoucherRepository.save(percentVoucherInterface);
    }

    @BeforeEach
    void clearUpEach() {
        fileVoucherRepository.deleteAll();
    }


    @Test
    @DisplayName("아이디로 메모리에 기록된 바우처를 조회할 수 있다.")
    void findById() {
        Optional<VoucherInterface> findFixedVoucherInterfaceById = fileVoucherRepository.findById(fixedVoucherInterface.getVoucherId());
        Optional<VoucherInterface> findPercentVoucherInterfaceById = fileVoucherRepository.findById(percentVoucherInterface.getVoucherId());
        assertThat(findFixedVoucherInterfaceById.get(), samePropertyValuesAs(fixedVoucherInterface));
        assertThat(findPercentVoucherInterfaceById.get(), samePropertyValuesAs(percentVoucherInterface));
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void findAllVouchers() {
        List<VoucherInterface> allVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(allVouchers, hasSize(2));
    }

    @Test
    @DisplayName("FIXED 바우처를 저장할 수 있다.")
    void save() {
        VoucherInterface fixedVoucherInterface = new FixedDiscountVoucherInterface(UUID.randomUUID(), 20L);
        VoucherInterface percentVoucherItnerface = new PercentDiscountVoucherInterface(UUID.randomUUID(), 20L);
        fileVoucherRepository.save(fixedVoucherInterface);
        fileVoucherRepository.save(percentVoucherItnerface);

        List<VoucherInterface> allVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(allVouchers, hasSize(4));
        // 이 부분 추가해야할 듯?
    }

    @Test
    @DisplayName("메모리에 기록된 모든 바우처를 삭제할 수 있다.")
    void deleteAll() {
        List<VoucherInterface> beforeVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(beforeVouchers, hasSize(2));
        fileVoucherRepository.deleteAll();

        List<VoucherInterface> afterVouchers = fileVoucherRepository.findAllVouchers();
        assertThat(afterVouchers, hasSize(0));
    }
}