package study.dev.spring.voucher.infrastructure;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.domain.VoucherType;

@Repository
@Profile("prod")
public class JdbcVoucherRepository implements VoucherRepository {

	private static final String INSERT = "insert into Voucher VALUES (:uuid, :name, :voucherType, :discountAmount)";
	private static final String DELETE_BY_ID = "delete from Voucher v where v.uuid = :uuid";
	private static final String FIND_ALL = "select * from Voucher";
	private static final String FIND_BY_ID = "select * from Voucher v where v.uuid = :uuid";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Voucher> rowMapper;

	public JdbcVoucherRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		rowMapper = (rs, rowNum) -> new Voucher(
			rs.getString("uuid"),
			rs.getString("name"),
			VoucherType.valueOf(rs.getString("voucherType")),
			rs.getDouble("discountAmount")
		);
	}

	@Override
	public Voucher save(Voucher voucher) {
		MapSqlParameterSource paramSource = new MapSqlParameterSource()
			.addValue("uuid", voucher.getUuid())
			.addValue("name", voucher.getName())
			.addValue("voucherType", voucher.getTypeName())
			.addValue("discountAmount", voucher.getDiscountAmount());

		jdbcTemplate.update(INSERT, paramSource);
		return voucher;
	}

	@Override
	public Optional<Voucher> findById(String uuid) {
		try {
			Voucher voucher = jdbcTemplate.queryForObject(
				FIND_BY_ID,
				Collections.singletonMap("uuid", uuid),
				rowMapper
			);
			return Optional.ofNullable(voucher);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Voucher> findAll() {
		return jdbcTemplate.query(FIND_ALL, rowMapper);
	}

	@Override
	public void deleteById(String uuid) {
		jdbcTemplate.update(DELETE_BY_ID, Collections.singletonMap("uuid", uuid));
	}
}
