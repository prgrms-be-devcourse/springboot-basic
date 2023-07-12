package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JDBCRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.example.demo.voucher.infrastructure"}
    )
    static class config {

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(H2)
                    .setScriptEncoding("UTF-8")
                    .ignoreFailedDrops(true)
                    .addScript("h2.sql")
                    .build();

//            return DataSourceBuilder.create()
//                    .url("jdbc:mysql://localhost/spring_basic")
//                    .username("root")
//                    .password("1234")
//                    .type(HikariDataSource.class)
//                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JDBCRepository jdbcRepository;

    private Voucher voucher;

    @BeforeEach
    void setUp() {
        UUID voucherId = UUID.randomUUID();
        long value = 100L;
        voucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(voucherId, value);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "vouchers");
    }

    @Test
    @DisplayName("아이디로 조회")
    void findById() {
        // Given
        jdbcRepository.insert(voucher);

        // When
        Optional<Voucher> foundVoucher = jdbcRepository.findById(voucher.getVoucherId());

        // Then
        assertTrue(foundVoucher.isPresent());
        assertThat(foundVoucher.get()).isEqualToComparingFieldByField(voucher);
    }

    @Test
    @DisplayName("모든 데이터 검색")
    void findAll() {
        // Given
        jdbcRepository.insert(voucher);

        // When
        List<Voucher> vouchers = jdbcRepository.findAll();

        // Then
        assertThat(vouchers).hasSize(1);
        assertThat(vouchers.get(0)).isEqualToComparingFieldByField(voucher);
    }

    @Test
    @DisplayName("바우처 삽입")
    void insert() {
        // Given
        // voucher is set up in the BeforeEach block

        // When
        jdbcRepository.insert(voucher);

        // Then
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "vouchers");
        assertEquals(count, 1);
        //assertThat(count).isEqualTo(1);
    }
}

