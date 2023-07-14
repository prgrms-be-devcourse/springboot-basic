package com.prgms.voucher.voucherproject.repository.voucher;

import com.prgms.voucher.voucherproject.domain.voucher.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgms.voucher.voucherproject.util.JdbcUtils.toUUID;


@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var vocuherType = resultSet.getString("voucher_type");
        var discount = resultSet.getLong("discount");

        switch (vocuherType) {
            case "fixed" -> {
                return new FixedAmountVoucher(voucherId, discount);
            }
            case "percent" -> {
                return new PercentDiscountVoucher(voucherId, discount);
            }
        }
        return null;
    };

//    private Map<String, Object> toParamMap(Product product) {
//        var paramMap = new HashMap<String, Object>();
//        paramMap.put("productId", product.getProductId().toString().getBytes());
//        paramMap.put("productName", product.getProductName());
//        paramMap.put("category", product.getCategory().toString());
//        paramMap.put("price", product.getPrice());
//        paramMap.put("description", product.getDescription());
//        paramMap.put("createdAt", product.getCreatedAt());
//        paramMap.put("updatedAt", product.getUpdatedAt());
//        return paramMap;
//    }
}
