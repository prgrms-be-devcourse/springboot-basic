package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Profile("prod")
@Repository
@RequiredArgsConstructor
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String IS_BLACKLISTED = "is_blacklisted";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> findAllBlacklistedCustomers() {
        String query = """
                        SELECT *
                        FROM customer
                        WHERE is_blacklisted = TRUE
                """;

        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> mapCustomerFromResultSet(rs));
    }

    @Override
    public Customer save(Customer customer) {
        String query = """
                        INSERT INTO customer (id, name, is_blacklisted)
                        VALUES (UUID_TO_BIN(:id), :name, :is_blacklisted)
                """;

        jdbcTemplate.update(query, mapCustomerParameters(customer));
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String query = """
                        SELECT *
                        FROM customer
                        WHERE id = UUID_TO_BIN(:id)
                        FOR UPDATE
                """;

        List<Customer> customers = jdbcTemplate.query(
                query,
                Map.of(ID, customerId.toString()),
                (rs, rowNum) -> mapCustomerFromResultSet(rs));

        return customers.stream().findFirst();
    }

    @Override
    public int update(Customer customer) {
        String query = """
                        UPDATE customer
                        SET name = :name, is_blacklisted = :is_blacklisted
                        WHERE id = UUID_TO_BIN(:id)
                """;

        return jdbcTemplate.update(query, mapCustomerParameters(customer));
    }

    private Map<String, Object> mapCustomerParameters(Customer customer) {
        Map<String, Object> params = new HashMap<>();

        params.put(ID, customer.getId().toString());
        params.put(NAME, customer.getName());
        params.put(IS_BLACKLISTED, customer.isBlacklisted());

        return params;
    }

    private Customer mapCustomerFromResultSet(ResultSet rs) throws SQLException {
        UUID id = UUIDUtil.byteToUUID(rs.getBytes(ID));
        String name = rs.getString(NAME);
        boolean isBlacklisted = rs.getBoolean(IS_BLACKLISTED);

        return Customer.createCustomer(id, name, isBlacklisted);
    }
}
