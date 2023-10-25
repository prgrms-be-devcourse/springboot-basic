package com.programmers.springbasic.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Repository
@Profile("test")
public class JdbcVoucherRepository implements VoucherRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
		UUID id = UUID.fromString(rs.getString("voucher_id"));
		String type = rs.getString("voucher_type");

		if (VoucherType.PERCENT_DISCOUNT.name().equals(type)) {
			return new PercentDiscountVoucher(id, rs.getLong("percent"));
		} else if (VoucherType.FIXED_AMOUNT.name().equals(type)) {
			return new FixedAmountVoucher(id, rs.getLong("amount"));
		}

		throw new IllegalArgumentException("Unknown voucher type");
	};

	public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Voucher insert(Voucher voucher) {
		String sql = "INSERT INTO voucher (voucher_id, voucher_type, percent, amount) VALUES (?, ?, ?, ?)";

		Long percentValue = null;
		Long amountValue = null;

		if (voucher instanceof PercentDiscountVoucher) {
			percentValue = voucher.getDiscountValue();
		} else if (voucher instanceof FixedAmountVoucher) {
			amountValue = voucher.getDiscountValue();
		}

		jdbcTemplate.update(sql, voucher.getVoucherId(), voucher.getVoucherType().name(), percentValue, amountValue);
		return voucher;
	}

	@Override
	public Voucher update(Voucher voucher) {
		String updateForPercentDiscountVoucher = "UPDATE voucher SET percent=? WHERE voucher_id=?";
		String updateForFixedAmountVoucher = "UPDATE voucher SET amount=? WHERE voucher_id=?";

		if (voucher instanceof PercentDiscountVoucher) {
			jdbcTemplate.update(updateForPercentDiscountVoucher, voucher.getDiscountValue(), voucher.getVoucherId());
		} else if (voucher instanceof FixedAmountVoucher) {
			jdbcTemplate.update(updateForFixedAmountVoucher, voucher.getDiscountValue(), voucher.getVoucherId());
		}
		return voucher;
	}


	@Override
	public List<Voucher> findAll() {
		String sql = "SELECT * FROM voucher";
		return jdbcTemplate.query(sql, voucherRowMapper);
	}

	@Override
	public Optional<Voucher> findById(UUID id) {
		String sql = "SELECT * FROM voucher WHERE voucher_id = ?";
		return jdbcTemplate.query(sql, voucherRowMapper, id).stream().findFirst();
	}

	@Override
	public void deleteById(UUID id) {
		String sql = "DELETE FROM voucher WHERE voucher_id = ?";
		jdbcTemplate.update(sql, id);
	}
}
