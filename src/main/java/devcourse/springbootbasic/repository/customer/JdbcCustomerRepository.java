package devcourse.springbootbasic.repository.customer;

import devcourse.springbootbasic.domain.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// TODO: 리팩터링 필요
@Profile("prod")
@Repository
@RequiredArgsConstructor
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static UUID parseUUIDFromBytes(byte[] uuidBytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(uuidBytes);
        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    @Override
    public List<Customer> findAllBlacklistedCustomers() {
        String sql = """
                        SELECT *
                        FROM customer
                        WHERE is_blacklisted = TRUE
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            byte[] uuidBytes = rs.getBytes("id");
            UUID id = parseUUIDFromBytes(uuidBytes);
            String name = rs.getString("name");
            boolean isBlacklisted = rs.getBoolean("is_blacklisted");

            return Customer.createCustomer(id, name, isBlacklisted);
        });
    }

    @Override
    public Customer save(Customer customer) {
        String sql = """
                        INSERT INTO customer (id, name, is_blacklisted)
                        VALUES (UUID_TO_BIN(:id), :name, :is_blacklisted)
                """;

        jdbcTemplate.update(sql, Map.of(
                "id", customer.getId().toString(),
                "name", customer.getName(),
                "is_blacklisted", customer.isBlacklisted()
        ));

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = """
                        SELECT *
                        FROM customer
                        WHERE id = UUID_TO_BIN(:id)
                        FOR UPDATE
                """;

        return jdbcTemplate.query(sql, Map.of("id", customerId.toString()), (rs, rowNum) -> {
            byte[] uuidBytes = rs.getBytes("id");
            UUID id = parseUUIDFromBytes(uuidBytes);
            String name = rs.getString("name");
            boolean isBlacklisted = rs.getBoolean("is_blacklisted");

            return Customer.createCustomer(id, name, isBlacklisted);
        }).stream().findFirst();
    }

    @Override
    public int update(Customer customer) {
        String sql = """
                        UPDATE customer
                        SET name = :name, is_blacklisted = :is_blacklisted
                        WHERE id = UUID_TO_BIN(:id)
                """;

        return jdbcTemplate.update(sql, Map.of(
                "id", customer.getId().toString(),
                "name", customer.getName(),
                "is_blacklisted", customer.isBlacklisted()
        ));
    }
}
