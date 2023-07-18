package programmers.org.voucher.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;

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
        String sql = "INSERT INTO vouchers(discount_amount, type) VALUES (?, ?)";
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
        String sql = "SELECT * FROM vouchers";

        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(sql, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, VoucherRequest request) {
        String sql = "UPDATE vouchers SET discount_amount=? WHERE voucher_id=?";
        jdbcTemplate.update(sql, request.getDiscountAmount(), id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id=?";

        List<Voucher> vouchers = jdbcTemplate.query(sql, voucherRowMapper(), id);

        if (vouchers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(vouchers.get(0));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM vouchers WHERE voucher_id=?";
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
