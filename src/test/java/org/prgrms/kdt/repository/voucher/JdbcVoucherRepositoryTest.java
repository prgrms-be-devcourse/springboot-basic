package org.prgrms.kdt.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class JdbcVoucherRepositoryTest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.26")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("root1234!");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void setup() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처가 생성된다.")
    void testInsert() {
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(fixedVoucher);

        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.get(0), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    void testUpdate() {
        // given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(fixedVoucher);

        // when
        fixedVoucher.changeValue(100L);
        voucherRepository.update(fixedVoucher);

        // then
        Voucher updatedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId())
            .orElseThrow(() -> new IllegalArgumentException("Could not found voucher with voucherId=" + fixedVoucher.getVoucherId()));
        assertThat(updatedVoucher, samePropertyValuesAs(fixedVoucher));
    }

    @Test
    void testDelete() {
        // givne
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        voucherRepository.insert(fixedVoucher);

        // when
        voucherRepository.delete(fixedVoucher.getVoucherId());

        // then
        Optional<Voucher> deletedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());
        assertThat(deletedVoucher.isEmpty(), is(true));
    }
}