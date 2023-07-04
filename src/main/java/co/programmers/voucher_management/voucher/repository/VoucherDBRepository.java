package co.programmers.voucher_management.voucher.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

@Repository
@Profile("db")
public class VoucherDBRepository implements VoucherRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Voucher> voucherRowMapper = (rs, i) -> mapToVoucher(rs);
	private static final String INSERT_QUERY = "INSERT INTO voucher(discount_type, discount_amount) VALUES (:type, :amount)";
	private static final String SELECT_ALL_QUERY = "SELECT id,discount_type, discount_amount FROM voucher WHERE status='Y'";
	private static final String DELETE_QUERY = "UPDATE voucher SET status='N' WHERE id=:id;";

	public VoucherDBRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Voucher create(Voucher voucher) {
		SqlParameterSource sqlParameterSource = mapToParameterSource(voucher);
		jdbcTemplate.update(INSERT_QUERY, sqlParameterSource);
		return voucher;
	}

	private SqlParameterSource mapToParameterSource(Voucher voucher) {
		return new MapSqlParameterSource()
				.addValue("type", voucher.getDiscountStrategy().getType())
				.addValue("amount", voucher.getDiscountStrategy().getAmount());
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query(SELECT_ALL_QUERY, new MapSqlParameterSource(),
				voucherRowMapper);
	}

	@Override
	public void deleteOf(int id) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update(DELETE_QUERY, sqlParameterSource);
	}

	private Voucher mapToVoucher(ResultSet resultSet) {
		try {
			int id = resultSet.getInt("id");
			int amount = resultSet.getInt("discount_amount");
			String discountType = resultSet.getString("discount_type");
			DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, amount);
			return Voucher.builder()
					.id(id)
					.discountStrategy(discountStrategy)
					.build();
		} catch (SQLException sqlException) {
			throw new RuntimeException("DB Reader Failed");
		}
	}
}
