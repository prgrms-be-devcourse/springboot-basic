package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.GetResultFailedException;
import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.exceptions.InvalidParameterException;
import org.prgrms.kdt.exceptions.NoVoucherException;
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

        String type = resultSet.getString("type");
        VoucherType voucherType;
        try {
            voucherType = findVoucherTypeByInput(type);
        } catch (InvalidITypeInputException invalidTypeException) {
            throw new InvalidParameterException(
                    MessageFormat.format("저장소 내의 파라미터 [voucher Type -> {0}] 가 잘못되어 프로그램이 정상 동작할 수 없습니다. ", type),
                    invalidTypeException);
        }

        Integer amount = resultSet.getInt("amount");
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
            default -> throw new InvalidParameterException(
                    MessageFormat.format("저장소 내의 파라미터 [voucher Type -> {0}] 가 잘못되어 프로그램이 정상 동작할 수 없습니다. ", type));
        }
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static Map<String, Object> createParamMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId(),
                "type", voucher.getVoucherType(),
                "amount", voucher.getAmount(),
                "customerId", voucher.getOwnerId().orElse("")
        );

    }

    @Override
    public void save(Voucher voucher) {
        Map<String, Object> voucherParaMap = createParamMap(voucher);
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
        try {
            return namedParameterJdbcTemplate.query("select * from voucher", voucherRowMapper);
        } catch (InvalidITypeInputException invalidITypeInputException) {
            throw new GetResultFailedException("저장소 내부 문제로 결과를 가져올 수 없습니다.", invalidITypeInputException);
        }
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject("select * from voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId), voucherRowMapper));
        } catch (EmptyResultDataAccessException noResult) {
            logger.info("{} 로 해당되는 바우처가 존재하지 않습니다.", voucherId, noResult);
        } catch (InvalidParameterException invalidVoucherType) {
            throw new GetResultFailedException("저장소 내부 문제로 결과를 가져올 수 없습니다.", invalidVoucherType);
        }
        return Optional.empty();
    }

    public void deleteById(String voucherId) {
        int update = namedParameterJdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = :voucherId", Collections.singletonMap("voucherId", voucherId));
        if (update != UPDATE_SUCCESS) {
            throw new NoVoucherException(
                    MessageFormat.format(
                            "전달받은 ID에 대한 삭제를 할 수 없습니다. 사유: 해당 ID -> [{0}] 를 가진 바우처를 찾을 수 없음.", voucherId));
        }
    }
}
