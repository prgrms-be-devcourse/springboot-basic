package org.prgrms.kdtspringdemo.voucher.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.prgrms.kdtspringdemo.voucher.exception.VoucherExceptionMessage.*;

public enum VoucherQuery {
    CREATE("INSERT INTO voucher(voucher_id, voucher_type, amount) VALUES(UUID_TO_BIN(:voucher_id), :voucher_type, :amount)"),
    FIND_ID("SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)"),
    FIND_ALL("SELECT * FROM voucher"),
    UPDATE("UPDATE voucher SET voucher_type = :voucher_type, amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucher_id)"),
    DELETE("DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucher_id)");

    private static final Logger logger = LoggerFactory.getLogger(VoucherQuery.class);

    private final String query;

    public String getQuery() {
        return query;
    }

    VoucherQuery(String query) {
        this.query = query;
    }

    public static VoucherQuery findVoucherCommand(String userCommand) {
        return Arrays.stream(VoucherQuery.values())
                .filter(voucherSymbol -> voucherSymbol.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, NOT_FOUND_VOUCHER_COMMAND_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_VOUCHER_COMMAND_TYPE.getMessage());
                });
    }
}
