package programmers.org.voucher.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.repository.util.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

import static programmers.org.voucher.repository.util.constant.Operator.EQUALS;

@Component
@Primary
class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        Map<String, Object> map = new HashMap<>();
        map.put("discount_amount", "?");
        map.put("type", "?");

        Insert insert = Insert.builder()
                .insert(Voucher.class)
                .values(map)
                .build();

        String sql = insert.getQuery();

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
        Select select = Select.builder()
                .select()
                .from(Voucher.class)
                .build();

        String sql = select.getQuery();
        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(sql, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, int discountAmount) {
        Where where = Where.builder("voucher_id", EQUALS, "?").build();

        Update update = Update.builder()
                .update(Voucher.class)
                .set("discount_amount", "?")
                .where(where)
                .build();

        String sql = update.getQuery();

        jdbcTemplate.update(sql, discountAmount, id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        Where where = Where.builder("voucher_id", EQUALS, "?").build();

        Select select = Select.builder()
                .select()
                .from(Voucher.class)
                .where(where)
                .build();

        String sql = select.getQuery();

        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper(), id);

        if (vouchers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vouchers.get(0));
    }

    @Override
    public void deleteById(Long id) {
        Where where = Where.builder("voucher_id", EQUALS, "?").build();

        Delete delete = Delete.builder()
                .delete(Voucher.class)
                .where(where)
                .build();

        String sql = delete.getQuery();

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
