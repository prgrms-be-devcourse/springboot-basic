package org.prgrms.kdt.repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcVoucherRepositoryTest {
    private VoucherRepository repository;
    private JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepositoryTest() {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("root")
                .type(MysqlDataSource.class)
                .build();
        Resource resource = new ClassPathResource("data.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.repository = new JdbcVoucherRepository(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("insert into voucher (discount_amount, voucher_type) values (10, \"PERCENT_DISCOUNT_VOUCHER\")");
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("truncate voucher;");
    }

    @Test
    @DisplayName("[성공] 바우처 저장하기")
    void save() {
        VoucherType voucherType = VoucherType.PERCENT_DISCOUNT_VOUCHER;
        double discountAmount = 10;
        Voucher newVoucher = new Voucher(voucherType, discountAmount);

        Optional<Voucher> returnedVoucher = repository.saveVoucher(newVoucher);

        Assertions.assertTrue(returnedVoucher.isPresent());
    }

    @Test
    @DisplayName("[성공] 바우처 아이디로 조회하기")
    void getById() {
        long voucherId = 1;

        Optional<Voucher> voucherById = repository.getVoucherById(voucherId);

        Assertions.assertEquals(voucherId, voucherById.get().getId());
    }

    @Test
    @DisplayName("[실패] 존재하지 않는 바우처 아이디로 조회하기")
    void getById_withInvalidId() {
        long invalidVoucherId = 0;

        Optional<Voucher> voucherById = repository.getVoucherById(invalidVoucherId);

        Assertions.assertFalse(voucherById.isPresent());
    }

    @Test
    @DisplayName("[성공] 모든 바우처를 가져오기")
    void getAllVoucher() {
        List<Voucher> allVoucher = repository.getAllVouchers();

        Assertions.assertEquals(1, allVoucher.size());
    }
}
