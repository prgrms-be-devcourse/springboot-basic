package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.InvalidParameterException;
import org.prgrms.kdt.exceptions.InvalidDBAccessException;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;

import static org.prgrms.kdt.utils.VoucherType.findVoucherTypeByInput;

@Profile("jdbc")
@Repository
public class JdbcVoucherStorage implements VoucherStorage {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherStorage.class);
    private static final int UPDATE_SUCCESS = 1;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        String voucherId = resultSet.getString("voucher_id");
        VoucherType voucherType = findVoucherTypeByInput(resultSet.getString("type"));
        int amount = resultSet.getInt("amount");
        String customerId = resultSet.getString("customer_id");
        switch (voucherType) {
            case FIXED_VOUCHER -> {
                if (customerId == null) {
                    return new FixedAmountVoucher(voucherId, amount);
                }
                return new FixedAmountVoucher(voucherId, amount, customerId);
            }
            case PERCENT_VOUCHER -> {
                if (customerId == null) {
                    return new PercentDiscountVoucher(voucherId, amount);
                }
                return new PercentDiscountVoucher(voucherId, amount, customerId);
            }
            default -> throw new InvalidDBAccessException("잘못된 타입 값 -> " + voucherType);
        }
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static Map<String, Object> createParaMap(Voucher voucher) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("voucherId", voucher.getVoucherId());
        paraMap.put("type", voucher.getVoucherType());
        paraMap.put("amount", voucher.getAmount());
        paraMap.put("customerId", voucher.getOwnerId().orElse(null));

        return paraMap;
    }

    @Override
    public void save(Voucher voucher) {
        Map<String, Object> voucherParaMap = createParaMap(voucher);
        int update = namedParameterJdbcTemplate.update(
                "INSERT INTO voucher(voucher_id, type, amount, customer_id) VALUES (:voucherId, :type, :amount, :customerId)",
                voucherParaMap);
        if (update != UPDATE_SUCCESS) {
            String errorDescription = MessageFormat.format("고객을 저장할 수 없습니다. 입력된 값을 확인해주세요. voucherId -> {0}, type -> {}, amount -> {}, customerId -> {}"
                    , voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount(), voucher.getOwnerId());
            throw new InvalidParameterException(errorDescription);
        }
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject("select * from voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId), voucherRowMapper));
        } catch (EmptyResultDataAccessException noResult) {
            logger.info("{} 로 해당되는 바우처가 존재하지 않습니다.", voucherId, noResult);
        } catch (InvalidParameterException invalidVoucherType) {
            logger.error("바우처를 생성할 수 없습니다. DB에 저장된 바우처 타입이 유효하지 않습니다. {}", invalidVoucherType.getMessage(), invalidVoucherType);
        }
        return Optional.empty();
    }

    public void deleteById(String voucherId) {
        int update = namedParameterJdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId));
        if (update != UPDATE_SUCCESS) {
            throw new InvalidParameterException(
                    MessageFormat.format(
                            "전달받은 ID에 대한 삭제가 성공하지 않았습니다. 사유: 해당 ID -> [{0}] 를 가진 바우처를 찾을 수 없음.", voucherId));
        }
    }
}
