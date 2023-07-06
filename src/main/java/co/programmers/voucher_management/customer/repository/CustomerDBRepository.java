package co.programmers.voucher_management.customer.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.customer.entity.Customer;

@Repository
@Profile({"local","db"})
public class CustomerDBRepository implements CustomerRepository {
	private static final String SELECT_BY_ID_QUERY = "SELECT id, name, phone_number, rating FROM customer WHERE id = :id AND STATUS = 'Y'";
	private static final String SELECT_BY_RATING_QUERY = "SELECT id, name, phone_number, rating FROM customer WHERE rating = :rating AND STATUS = 'Y'";
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Customer> CustomerRowMapper = (rs, i) -> mapToCustomer(rs);

	public CustomerDBRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Customer> findById(long id) {
		List<Customer> foundResult = jdbcTemplate.query(SELECT_BY_ID_QUERY,
				new MapSqlParameterSource().addValue("id", id), CustomerRowMapper);
		return Optional.ofNullable(foundResult.get(0));
	}

	private Customer mapToCustomer(ResultSet resultSet) {
		try {
			long id = resultSet.getLong("id");
			String name = resultSet.getString("name");
			String phoneNumber = resultSet.getString("phone_number");
			String rating = resultSet.getString("rating");
			return Customer.builder()
					.id(id)
					.name(name)
					.rating(rating)
					.phoneNumber(phoneNumber)
					.build();
		} catch (SQLException sqlException) {
			throw new RuntimeException("DB Reader Failed");
		}
	}

	@Override
	public List<Customer> findByRating(String rating) {
		return jdbcTemplate.query(SELECT_BY_RATING_QUERY,
				new MapSqlParameterSource().addValue("rating", rating), CustomerRowMapper);
	}
}
