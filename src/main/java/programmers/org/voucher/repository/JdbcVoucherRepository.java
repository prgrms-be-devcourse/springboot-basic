package programmers.org.voucher.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;
import programmers.org.voucher.repository.util.*;
import programmers.org.voucher.repository.util.statement.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static programmers.org.voucher.repository.util.constant.Table.VOUCHERS;

public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        Insert insert = new Insert.Builder()
                .query(VOUCHERS)
                .build();

        Values values = new Values.Builder()
                .query("discount_amount")
                .query("type")
                .build();

        String sql = QueryGenerator.toQuery(insert, values);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setInt(1, voucher.getDiscountAmount());
            statement.setString(2, voucher.getType().toString());
            return statement;
        }, keyHolder);
    }

    @Override
    public List<Voucher> getAll() {
        Select select = new Select.Builder()
                .query("*")
                .build();

        From from = new From.Builder()
                .query(VOUCHERS)
                .build();

        String sql = QueryGenerator.toQuery(select, from);

        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(sql, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, VoucherRequest request) {
        Update update = new Update.Builder()
                .query(VOUCHERS)
                .build();

        Set set = new Set.Builder()
                .query("discount_amount")
                .build();

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = QueryGenerator.toQuery(update, set, where);

        jdbcTemplate.update(sql, request.getDiscountAmount(), id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        Select select = new Select.Builder()
                .query("*")
                .build();

        From from = new From.Builder()
                .query(VOUCHERS)
                .build();

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = QueryGenerator.toQuery(select, from, where);
        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper(), id);

        if (vouchers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vouchers.get(0));
    }

    @Override
    public void deleteById(Long id) {
        Delete delete = new Delete.Builder()
                .query(VOUCHERS)
                .build();

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = QueryGenerator.toQuery(delete, where);

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (resultSet, rowNum) -> {
            long id = resultSet.getLong("voucher_id");
            int discountAmount = resultSet.getInt("discount_amount");
            VoucherType type = VoucherType.find(resultSet.getString("type"));

            return new Voucher(id, discountAmount, type);
        };
    }
}
