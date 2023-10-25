package study.dev.spring.customer.infrastructure;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

	private static final String INSERT = "INSERT INTO Customer VALUES (:uuid, :name)";
	private static final String FIND_ALL = "select * from Customer";
	private static final String FIND_BY_ID = "select * from Customer c where c.uuid = :uuid";

	private static final String UUID = "uuid";
	private static final String NAME = "name";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Customer> rowMapper;

	public JdbcCustomerRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		rowMapper = (rs, rowNum) -> new Customer(
			rs.getString(UUID),
			rs.getString(NAME)
		);
	}

	@Override
	public Customer save(Customer customer) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue(UUID, customer.getUuid())
			.addValue(NAME, customer.getName());

		jdbcTemplate.update(INSERT, parameterSource);
		return customer;
	}

	@Override
	public Optional<Customer> findById(String uuid) {
		try {
			Customer customer = jdbcTemplate.queryForObject(
				FIND_BY_ID,
				Collections.singletonMap(UUID, uuid),
				rowMapper
			);
			return Optional.ofNullable(customer);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query(FIND_ALL, rowMapper);
	}
}
