package com.pgms.part1.domain.voucher.repository;

import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.exception.ErrorCode;
import com.pgms.part1.exception.VoucherApplicationException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Voucher> findVoucherById(Long id) {
        String findVoucherByIdSql = "SELECT * FROM vouchers WHERE id = ?";
        Voucher voucher;
        try{
            voucher = jdbcTemplate.queryForObject(findVoucherByIdSql, new Object[] {id}, (resultSet, i) ->
                    mapVoucher(resultSet));
        } catch(EmptyResultDataAccessException e){
            throw new VoucherApplicationException(ErrorCode.VOUCHER_NOT_EXIST);
        }
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findVoucherByFilter(String date, VoucherDiscountType type) {
        StringBuilder sql = new StringBuilder("SELECT * FROM vouchers");

        List<Object> parameterList = new ArrayList<>();

        boolean isFirstParameter = true;

        if(date != null || type != null){
            sql.append(" where");

            if(date != null){
                parameterList.add(LocalDate.parse(date));
                sql.append(" created_at = ?");
                isFirstParameter = false;
            }
            if(type != null){
                if(!isFirstParameter)
                    sql.append(" and");
                parameterList.add(type);
                sql.append(" discount_type = ?");
            }
        }

        return jdbcTemplate.query(sql.toString(), parameterList.toArray(), (resultSet, i) -> mapVoucher(resultSet));
    }
}
