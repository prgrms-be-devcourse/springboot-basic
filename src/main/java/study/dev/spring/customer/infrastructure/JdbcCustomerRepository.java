package study.dev.spring.customer.infrastructure;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;

public class JdbcCustomerRepository implements CustomerRepository {

	private static final String FIND_ALL = "select * from Customer";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Customer> rowMapper;

	public JdbcCustomerRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		rowMapper = (rs, rowNum) -> new Customer(
			rs.getString("uuid"),
			rs.getString("name")
		);
	}

	@Override
	public List<Customer> findAll() {
		return jdbcTemplate.query(FIND_ALL, rowMapper);
	}
}
