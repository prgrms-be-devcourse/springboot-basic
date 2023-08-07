package org.prgrms.kdtspringdemo.customer.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.prgrms.kdtspringdemo.customer.exception.CustomerExceptionMessage.*;

public enum CustomerCommand {
    CREATE,
    FIND_ID,
    FIND_NICKNAME,
    FIND_ALL,
    UPDATE,
    DELETE
    ;

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommand.class);

    public static CustomerCommand findCustomerCommand(String userCommand) {
        return Arrays.stream(CustomerCommand.values())
                .filter(customerSymbol -> customerSymbol.name().equals(userCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("원인 : {} -> 에러 메시지 : {}", userCommand, NOT_FOUND_CUSTOMER_COMMAND_TYPE.getMessage());
                    throw new IllegalArgumentException(NOT_FOUND_CUSTOMER_COMMAND_TYPE.getMessage());
                });
    }
}
