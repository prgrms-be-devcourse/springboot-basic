package programmers.org.voucher.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.repository.util.sqlBuilder.DeleteBuilder;
import programmers.org.voucher.repository.util.sqlBuilder.InsertBuilder;
import programmers.org.voucher.repository.util.sqlBuilder.SelectBuilder;
import programmers.org.voucher.repository.util.sqlBuilder.UpdateBuilder;
import programmers.org.voucher.repository.util.statement.*;
import programmers.org.voucher.repository.util.statement.delete.Delete;
import programmers.org.voucher.repository.util.statement.insert.Insert;
import programmers.org.voucher.repository.util.statement.insert.Values;
import programmers.org.voucher.repository.util.statement.select.From;
import programmers.org.voucher.repository.util.statement.select.Select;
import programmers.org.voucher.repository.util.statement.update.Set;
import programmers.org.voucher.repository.util.statement.update.Update;

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
        Insert insert = new Insert(VOUCHERS);

        Values values = new Values.Builder()
                .query("discount_amount")
                .query("type")
                .build();

        String sql = new InsertBuilder()
                .insert(insert)
                .values(values)
                .build();

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

        From from = new From(VOUCHERS);

        String sql = new SelectBuilder()
                .select(select)
                .from(from)
                .build();

        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(sql, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, int discountAmount) {
        Update update = new Update(VOUCHERS);

        Set set = new Set.Builder()
                .query("discount_amount")
                .build();

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = new UpdateBuilder()
                .update(update)
                .set(set)
                .where(where)
                .build();

        jdbcTemplate.update(sql, discountAmount, id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        Select select = new Select.Builder()
                .query("*")
                .build();

        From from = new From(VOUCHERS);

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = new SelectBuilder()
                .select(select)
                .from(from)
                .where(where)
                .build();

        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper(), id);

        if (vouchers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vouchers.get(0));
    }

    @Override
    public void deleteById(Long id) {
        Delete delete = new Delete(VOUCHERS);

        Where where = new Where.Builder()
                .query("voucher_id")
                .build();

        String sql = new DeleteBuilder()
                .delete(delete)
                .where(where)
                .build();

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
