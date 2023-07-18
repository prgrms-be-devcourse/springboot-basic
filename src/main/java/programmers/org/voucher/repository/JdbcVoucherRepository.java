package programmers.org.voucher.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;
import programmers.org.voucher.repository.util.QueryGenerator;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        String sql = new QueryGenerator.Builder()
                .insertInto("vouchers")
                .values("discount_amount", "type")
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
        String sql = new QueryGenerator.Builder()
                .select("*")
                .from("vouchers")
                .build();

        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(sql, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, VoucherRequest request) {
        String sql = new QueryGenerator.Builder()
                .update("vouchers")
                .set("discount_amount")
                .where("voucher_id")
                .build();

        jdbcTemplate.update(sql, request.getDiscountAmount(), id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        String sql = new QueryGenerator.Builder()
                .select("*")
                .from("vouchers")
                .where("voucher_id")
                .build();

        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper(), id);

        if (vouchers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vouchers.get(0));
    }

    @Override
    public void deleteById(Long id) {
        String sql = new QueryGenerator.Builder()
                .deleteFrom("vouchers")
                .where("voucher_id")
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
