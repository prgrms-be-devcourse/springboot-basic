package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Profile({"dev", "test"})
@Repository
public class VoucherJdbcRepository implements VoucherRepository{

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Voucher mapVoucher(ResultSet resultSet) throws SQLException {
        return Voucher.newVocher(resultSet.getLong("id"), resultSet.getInt("discount"), VoucherDiscountType.valueOf(resultSet.getString("discount_type")));
    }

    @Override
    public List<Voucher> list() {
        String listVouchersSql = "SELECT * FROM VOUCHERS";
        return jdbcTemplate.query(listVouchersSql, (resultSet, i) ->
                mapVoucher(resultSet));
    }

    @Override
    public void add(Voucher voucher) {
        String addVoucherSql = "INSERT INTO VOUCHERS(id, discount, discount_type) values (?, ?, ?)";
        jdbcTemplate.update(addVoucherSql, voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType().toString());
    }

    @Override
    public void delete(Long id) {
        String deleteCustomerSql = "DELETE FROM VOUCHERS WHERE id = ?";
        jdbcTemplate.update(deleteCustomerSql, id);
    }

    @Override
    public List<Voucher> findVoucherByWallets(List<Wallet> wallet) {
        String inSql = String.join(",", Collections.nCopies(wallet.size(), "?"));

        Object[] ids = wallet.stream().map(Wallet::voucherId).toArray();

        return jdbcTemplate.query(
                String.format("SELECT * FROM VOUCHERS WHERE id IN (%s)", inSql), ids,
                (resultSet, i) -> mapVoucher(resultSet));
    }
}
