package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.exception.VoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile({"default"})
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher findById(UUID voucherId) throws VoucherException {
        return null;
    }

    @Override
    public List<Voucher> findAll() throws VoucherException {
        return null;
    }

    @Override
    public Voucher save(Voucher voucher) throws VoucherException {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("id", voucher.getVoucherId().toString());
            put("name", "demo");
            put("type", voucher.getVoucherType());
            put("figure", voucher.getVoucherFigure());
        }};
        int result = jdbcTemplate.update("INSERT INTO voucher(id, name, type, figure) " +
                        "VALUES (UNHEX(REPLACE(:id, '-', '')), :name, :type, :figure)",
                paramMap);
        if (result == 1) {
            return voucher;
        }
        throw new RuntimeException("바우처 저장에 실패하였습니다.");
    }
}
