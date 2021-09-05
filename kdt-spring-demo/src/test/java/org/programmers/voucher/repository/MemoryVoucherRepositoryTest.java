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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.voucher.repository"})
    static class Config {

        @Bean
        public FileMemoryVoucherRepository fileMemoryVoucherRepository() {
            return new MemoryVoucherRepository();
        }
    }

    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    PercentDiscountVoucherInterface percentDiscountVoucherInterface;
    FixedDiscountVoucherInterface fixedDiscountVoucherInterface;

    @BeforeEach
    void setUp() {
        percentDiscountVoucherInterface = new PercentDiscountVoucherInterface(UUID.randomUUID(), 10L);
        fixedDiscountVoucherInterface = new FixedDiscountVoucherInterface(UUID.randomUUID(), 10L);
        memoryVoucherRepository.save(percentDiscountVoucherInterface);
        memoryVoucherRepository.save(fixedDiscountVoucherInterface);
    }

    @AfterEach
    void clearUp() {
        memoryVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("아이디를 통해서 조회할 수 있다.")
    @Order(1)
    void findById() {
        Optional<VoucherInterface> fixedVoucherInterfaceById = memoryVoucherRepository.findById(fixedDiscountVoucherInterface.getVoucherId());
        Optional<VoucherInterface> percentVoucherInterfaceById = memoryVoucherRepository.findById(percentDiscountVoucherInterface.getVoucherId());
        assertThat(fixedVoucherInterfaceById.get(), samePropertyValuesAs(fixedDiscountVoucherInterface));
        assertThat(percentVoucherInterfaceById.get(), samePropertyValuesAs(percentDiscountVoucherInterface));
    }

    @Test
    @DisplayName("없는 아이디를 통해 조회한 것을 사용할 때 예외가 발생한다.")
    @Order(2)
    void findByIdException() {
        assertThrows(RuntimeException.class, () -> memoryVoucherRepository.findById(UUID.randomUUID()).get());
    }

    @Test
    @DisplayName("메모리를 통해 전체 조회할 수 있다.")
    @Order(3)
    void findAllVouchers() {
        List<VoucherInterface> allVouchers = memoryVoucherRepository.findAllVouchers();
        assertThat(allVouchers.isEmpty(), is(false));
        assertThat(allVouchers, hasSize(2));
    }

    @Test
    @DisplayName("메모리에 바우처를 저장할 수 있다.")
    @Order(4)
    void save() {
        FixedDiscountVoucherInterface fixedDiscountVoucherInterface = new FixedDiscountVoucherInterface(UUID.randomUUID(), 50L);
        memoryVoucherRepository.save(fixedDiscountVoucherInterface);

        Optional<VoucherInterface> findVoucherById = memoryVoucherRepository.findById(fixedDiscountVoucherInterface.getVoucherId());
        assertThat(findVoucherById.get(), samePropertyValuesAs(fixedDiscountVoucherInterface));
    }
}