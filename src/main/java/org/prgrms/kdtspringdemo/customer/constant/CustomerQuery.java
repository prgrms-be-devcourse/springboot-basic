package org.prgrms.kdtspringdemo.customer.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.prgrms.kdtspringdemo.customer.exception.CustomerExceptionMessage.*;

public enum CustomerQuery {
    CREATE("INSERT INTO customer(customer_id, nickname) VALUES(UUID_TO_BIN(:customer_id), :nickname)"),
    FIND_ID("SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customer_id)"),
    FIND_NICKNAME("SELECT * FROM customer WHERE nickname = :nickname"),
    ;
    private static final Logger logger = LoggerFactory.getLogger(CustomerQuery.class);

    private final String query;

    CustomerQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public static CustomerQuery findCustomCommand(String userCommand) {
        return Arrays.stream(CustomerQuery.values())
                .filter(customSymbol -> customSymbol.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, NOT_FOUND_CUSTOMER_COMMAND_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_CUSTOMER_COMMAND_TYPE.getMessage());
                });
    }
}
