package org.prgrms.kdt.dao.repository.voucher;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.voucher.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
class JdbcVoucherRepositoryTest {

    private static final String FIXED_TYPE = "FixedAmountVoucher";

    @Container
    static MySQLContainer<?> mySQLContainer = (MySQLContainer<?>) new MySQLContainer("mysql:8.0")
            .withDatabaseName("test-order_mgmt")
            .withUsername("test")
            .withPassword("test1234!")
            .withInitScript("init.sql");

    private static VoucherRepository voucherRepository;

    private static Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), "1000", VoucherType.of(FIXED_TYPE), LocalDateTime.now());

    @BeforeAll
    public static void beforeAll() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mySQLContainer.getJdbcUrl());
        config.setUsername(mySQLContainer.getUsername());
        config.setPassword(mySQLContainer.getPassword());

        DataSource ds = new HikariDataSource(config);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
        voucherRepository = new JdbcVoucherRepository(jdbcTemplate, new VoucherBuilder());
    }

    @Test
    @Order(1)
    @DisplayName("바우처를 DB에 삽입할 수 있고 ID를 이용해서 찾을 수 있다.")
    void insert() {
        /// given
        voucherRepository.insert(voucher);

        // when
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        // then
        assertThat(findVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @Order(2)
    @DisplayName("DB에 저장된 모든 바우처를 조회할 수 있다.")
    void findAllStoredVoucher() {
        // given
        List<Voucher> allStoredVoucher = voucherRepository.getAllStoredVoucher();

        // when, then
        assertThat(allStoredVoucher.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("DB에 저장된 바우처의 소유한 고객 ID 정보를 변경할 수 있다.")
    void updateVoucher() {
        // given
        UUID newOwnedCustomerId = UUID.randomUUID();
        voucher.setOwnedCustomerId(newOwnedCustomerId);

        // when
        Voucher updateVoucher = voucherRepository.update(voucher);
        Voucher findVoucher = voucherRepository.findById(updateVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher.getOwnedCustomerId().get(), is(newOwnedCustomerId));
    }

    @Test
    @Order(4)
    @DisplayName("DB에 저장된 바우처의 소유한 고객을 할당해제 할 수 있다.")
    void removeOwnedAssignment() {
        // given
        voucher.setOwnedCustomerId(null);

        // when
        Voucher updateVoucher = voucherRepository.update(voucher);
        Voucher findVoucher = voucherRepository.findById(updateVoucher.getVoucherId()).get();

        // then
        assertThat(findVoucher.getOwnedCustomerId().isEmpty(), is(true));
    }
}