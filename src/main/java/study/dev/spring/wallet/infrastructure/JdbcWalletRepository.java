package study.dev.spring.wallet.infrastructure;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import study.dev.spring.wallet.domain.Wallet;
import study.dev.spring.wallet.domain.WalletRepository;

@Repository
public class JdbcWalletRepository implements WalletRepository {

	private static final String INSERT = "INSERT INTO Wallet VALUES (:uuid, :customerId, :voucherId)";
	private static final String FIND_BY_ID = "select * from Wallet w where w.uuid = :uuid";
	private static final String FIND_BY_CUSTOMER_ID = "select * from Wallet w where w.customerId = :customerId";
	private static final String FIND_BY_VOUCHER_ID = "select * from Wallet w where w.voucherId = :voucherId";
	private static final String DELETE_BY_CUSTOMER_ID = "delete from Wallet w where w.customerId = :customerId";

	private static final String CUSTOMER_ID = "customerId";
	private static final String VOUCHER_ID = "voucherId";
	private static final String UUID = "uuid";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final RowMapper<Wallet> rowMapper;

	public JdbcWalletRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.rowMapper = ((rs, rowNum) -> new Wallet(
			rs.getString(UUID),
			rs.getString(CUSTOMER_ID),
			rs.getString(VOUCHER_ID)
		));
	}

	@Override
	public Wallet save(Wallet wallet) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource()
			.addValue(UUID, wallet.getUuid())
			.addValue(CUSTOMER_ID, wallet.getCustomerId())
			.addValue(VOUCHER_ID, wallet.getVoucherId());

		jdbcTemplate.update(INSERT, parameterSource);
		return wallet;
	}

	@Override
	public Optional<Wallet> findById(String uuid) {
		try {
			Wallet wallet = jdbcTemplate.queryForObject(
				FIND_BY_ID,
				Collections.singletonMap(UUID, uuid),
				rowMapper
			);
			return Optional.ofNullable(wallet);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Wallet> findByCustomerId(String customerId) {
		return jdbcTemplate.query(
			FIND_BY_CUSTOMER_ID,
			Collections.singletonMap(CUSTOMER_ID, customerId),
			rowMapper
		);
	}

	@Override
	public List<Wallet> findByVoucherId(String voucherId) {
		return jdbcTemplate.query(
			FIND_BY_VOUCHER_ID,
			Collections.singletonMap(VOUCHER_ID, voucherId),
			rowMapper
		);
	}

	@Override
	public void deleteByCustomerId(String customerId) {
		jdbcTemplate.update(
			DELETE_BY_CUSTOMER_ID,
			Collections.singletonMap(CUSTOMER_ID, customerId)
		);
	}
}
