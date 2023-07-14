package co.programmers.voucher_management.voucher.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

@Repository
@Profile("db")
public class VoucherDBRepository implements VoucherRepository {
	private static final String UPDATE_QUERY = "UPDATE voucher SET discount_type = :discount_type, discount_amount = :discount_amount, updated_at =:updated_at WHERE id = :id";
	private static final String INSERT_QUERY = "INSERT INTO voucher(discount_type, discount_amount) VALUES (:type, :amount)";
	private static final String SELECT_ALL_QUERY = "SELECT id,discount_type, discount_amount, customer_id FROM voucher WHERE status='Y'";
	private static final String SELECT_BY_ID_QUERY = "SELECT id,discount_type, discount_amount, customer_id FROM voucher WHERE status='Y' AND id=:id";
	private static final String SELECT_BY_CUSTOMER_QUERY = "SELECT id,discount_type, discount_amount, customer_id FROM voucher WHERE status='Y' AND customer_id=:customer_id";
	private static final String DELETE_QUERY = "UPDATE voucher SET status='N', updated_at=:updated_at WHERE id=:id";
	private static final String UPDATE_CUSTOMER_QUERY = "UPDATE voucher SET customer_id=:customer_id, updated_at=:updated_at WHERE id=:id";
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Voucher> voucherRowMapper = (rs, i) -> mapToVoucher(rs);

	public VoucherDBRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Voucher create(Voucher voucher) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("type", voucher.getDiscountStrategy().getType())
				.addValue("amount", voucher.getDiscountStrategy().getAmount());
		jdbcTemplate.update(INSERT_QUERY, sqlParameterSource);
		return voucher;
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query(SELECT_ALL_QUERY, new MapSqlParameterSource(), voucherRowMapper);
	}

	@Override
	public Optional<Voucher> findById(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("id", id);
		List<Voucher> result = jdbcTemplate.query(SELECT_BY_ID_QUERY, mapSqlParameterSource, voucherRowMapper);
		if (result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(result.get(0));
	}

	@Override
	public List<Voucher> findByCustomerId(long customerId) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("customer_id", customerId);
		return jdbcTemplate.query(SELECT_BY_CUSTOMER_QUERY, mapSqlParameterSource, voucherRowMapper);
	}

	@Override
	public void deleteById(long id) {
		findById(id).orElseThrow(
				() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", id)));
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("updated_at", LocalDateTime.now())
				.addValue("id", id);
		jdbcTemplate.update(DELETE_QUERY, sqlParameterSource);
	}

	@Override
	public Voucher update(Voucher voucher) {
		long id = voucher.getId();
		findById(id).orElseThrow(
				() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", id)));
		String discountType = voucher.getDiscountStrategy().getType();
		int discountAmount = voucher.getDiscountStrategy().getAmount();
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("id", id)
				.addValue("discount_type", discountType)
				.addValue("discount_amount", discountAmount)
				.addValue("updated_at", LocalDateTime.now());
		jdbcTemplate.update(UPDATE_QUERY, sqlParameterSource);
		return findById(id).orElseThrow(() -> new RuntimeException("Update failed"));
	}

	@Override
	public Voucher assignCustomer(Voucher voucher, Customer customer) {
		long voucherId = voucher.getId();
		long customerId = customer.getId();
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
				.addValue("id", voucherId)
				.addValue("updated_at", LocalDateTime.now())
				.addValue("customer_id", customerId);
		jdbcTemplate.update(UPDATE_CUSTOMER_QUERY, sqlParameterSource);
		return findById(voucherId).orElseThrow(() -> new RuntimeException("Assign failed"));
	}

	private Voucher mapToVoucher(ResultSet resultSet) {
		try {
			long id = resultSet.getLong("id");
			int amount = resultSet.getInt("discount_amount");
			String discountType = resultSet.getString("discount_type");
			DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, amount);
			long customerId = resultSet.getLong("customer_id");
			return Voucher.builder()
					.id(id)
					.discountStrategy(discountStrategy)
					.customerId(customerId)
					.build();
		} catch (SQLException sqlException) {
			throw new RuntimeException("DB reader failed");
		}
	}
}
