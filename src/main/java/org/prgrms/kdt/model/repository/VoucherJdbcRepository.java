package org.prgrms.kdt.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.entity.CustomerEntity;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("JdbcVoucherRepository")
public class VoucherJdbcRepository implements VoucherRepository{

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private static RowMapper<VoucherEntity> voucherEntityRowMapper =  (resultSet, i) -> {
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
			"INSERT INTO customers(voucher_id, amount, voucherType)VALUES (?, ?, ?)",
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
	public List<VoucherEntity> readAll() {
		return jdbcTemplate.query("select * from customers", voucherEntityRowMapper);
	}

	@Override
	public VoucherEntity saveVoucher(VoucherEntity voucherEntity) {
		int update = jdbcTemplate.update("UPDATE  vouchers SET amount = ?, voucher_type = ? WHERE customer_id = ?)",
			voucherEntity.getAmount(),
			voucherEntity.getVoucherType().toString()
		);
		if (update != 1) {
			throw new RuntimeException("Nothing was updated");
		}
		return voucherEntity;
	}
}
