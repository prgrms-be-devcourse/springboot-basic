package org.prgrms.kdtspringdemo.domain.blacklist.repository;

import org.prgrms.kdtspringdemo.domain.blacklist.model.BlackCustomer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.util.VoucherManagerUtil.toUUID;

@Repository
public class BlackListJdbcRepository implements BlackListRepository {
    private static final String FIND_BLACK_LIST_SQL = "SELECT customer_id, email, birth_date FROM customers_demo WHERE black_list = true";
    private static final RowMapper<BlackCustomer> blackCustomerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDate birth = resultSet.getDate("birth_date").toLocalDate();
        return new BlackCustomer(customerId, email, birth);
    };
    private final JdbcTemplate jdbcTemplate;

    public BlackListJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<BlackCustomer> findAllBlackCustomers() {
        return jdbcTemplate.query(FIND_BLACK_LIST_SQL, blackCustomerRowMapper);
    }
}
