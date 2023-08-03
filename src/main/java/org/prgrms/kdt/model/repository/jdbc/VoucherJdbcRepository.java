package org.prgrms.kdt.model.repository.jdbc;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("JdbcVoucherRepository")
public class VoucherJdbcRepository implements VoucherRepository {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private static RowMapper<VoucherEntity> voucherEntityRowMapper = (resultSet, i) -> {
		Long voucherId = resultSet.getLong("voucher_id");
		int amount = resultSet.getInt("amount");
		String voucherTypeString = resultSet.getString("voucher_type");
		VoucherType voucherType = VoucherType.valueOf(voucherTypeString);
		return new VoucherEntity(voucherId, amount, voucherType);
	};

	public VoucherJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public VoucherEntity createVoucher(VoucherEntity voucherEntity) {
		int update = jdbcTemplate.update(
			"INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES (?, ?, ?)",
			voucherEntity.getVoucherId(),
			voucherEntity.getAmount(),
			voucherEntity.getVoucherType().toString()
		);

		if (update != 1) {
			throw new RuntimeException("Nothing was created");
		}

		return voucherEntity;
	}

	@Override
	public List<VoucherEntity> findAll() {
		return jdbcTemplate.query("select * from vouchers", voucherEntityRowMapper);
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {
		int update = jdbcTemplate.update("UPDATE  vouchers SET amount = ?, voucher_type = ? WHERE voucher_id = ?",
			voucherEntity.getAmount(),
			voucherEntity.getVoucherType().toString(),
			voucherEntity.getVoucherId()
		);
		if (update != 1) {
			throw new RuntimeException("Nothing was updated");
		}
		return voucherEntity;
	}

	@Override
	public Optional<VoucherEntity> findById(Long voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
				"select * from vouchers WHERE voucher_id = ?",
				voucherEntityRowMapper,
				voucherId)
			);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean deleteById(Long voucherId) {
		int update = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = ?", voucherId);

		if (update != 1) {
			return false;
		}

		return true;
	}
}
