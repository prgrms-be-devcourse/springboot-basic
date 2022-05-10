package kdt.vouchermanagement.domain.customer.repository;

import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.entity.CustomerEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomerJdbcRepository implements CustomerRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String SAVE_SQL = "INSERT INTO customer(name) VALUES(:name)";
    private final String FIND_ALL_SQL = "SELECT * FROM customer";
    private final String FIND_BY_ID = "SELECT * FROM customer WHERE customer_id = :id";
    private final String DELETE_BY_ID = "DELETE FROM customer WHERE customer_id = :id";

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        if (customer == null) {
            throw new IllegalStateException();
        }

        int insertNum = jdbcTemplate.update(SAVE_SQL, toParamMap(CustomerEntity.from(customer)));
        if (insertNum != 1) {
            throw new IllegalStateException();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, customerEntityRowMapper)
                .stream()
                .map(CustomerEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(long id) {
        List<Customer> foundCustomers = jdbcTemplate.query(
                        FIND_BY_ID,
                        Map.of("id", id),
                        customerEntityRowMapper)
                .stream()
                .map(CustomerEntity::toDomain)
                .collect(Collectors.toList());
        return foundCustomers.isEmpty() ? Optional.empty() : Optional.of(foundCustomers.get(0));
    }

    @Override
    public void deleteById(long id) {
        int deleteNum = jdbcTemplate.update(DELETE_BY_ID, Map.of("id", id));
        if (deleteNum != 1) {
            throw new IllegalStateException();
        }
    }

    private static final RowMapper<CustomerEntity> customerEntityRowMapper = (resultSet, i) -> {
        long id = resultSet.getLong("customer_id");
        String name = resultSet.getString("name");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return CustomerEntity.of(id, name, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(CustomerEntity customerEntity) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", customerEntity.getName());
        return paramMap;
    }
}
