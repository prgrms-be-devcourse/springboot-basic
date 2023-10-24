package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Profile("dev")
@Repository
public class VoucherJdbcRepository implements VoucherRepository{

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Voucher mapVoucher(ResultSet resultSet) throws SQLException {
        if(resultSet.getString("discount_type").equals(VoucherDiscountType.FIXED_AMOUNT_DISCOUNT.toString())){
            return Voucher.newFixedAmountDiscountVoucher(resultSet.getLong("id"), resultSet.getInt("discount"));
        }
        else if(resultSet.getString("discount_type").equals(VoucherDiscountType.PERCENT_DISCOUNT.toString())){
            return Voucher.newPercentDiscountVoucher(resultSet.getLong("id"), resultSet.getInt("discount"));
        }
        else throw new RuntimeException("no such discount type!!");
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
    public void delete(Voucher voucher) {
        String deleteCustomerSql = "DELETE FROM VOUCHERS WHERE id = ?";
        jdbcTemplate.update(deleteCustomerSql, voucher.getId());
    }

    @Override
    public void findVoucherByWallet(Wallet wallet) {

    }
}
