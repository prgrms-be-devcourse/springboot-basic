package team.marco.voucher_management_system.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import team.marco.voucher_management_system.configuration.TestJdbcConfiguration;
import team.marco.voucher_management_system.configuration.TestPropertyConfiguration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig({TestPropertyConfiguration.class, TestJdbcConfiguration.class})
class JdbcVoucherRepositoryTest extends VoucherRepositoryTest {
    private static final String TRUNCATE_SQL = "DELETE FROM voucher;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Override
    protected VoucherRepository getRepository() {
        return jdbcVoucherRepository;
    }

    @BeforeAll
    @AfterEach
    void truncateTable() {
        jdbcTemplate.execute(TRUNCATE_SQL);
    }
}
