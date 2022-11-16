package com.example.springbootbasic.parser;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.springbootbasic.exception.ParserExceptionMessage.CSV_CUSTOMER_PARSER_EXCEPTION;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

public class CsvCustomerParser {
    private static final Logger logger = LoggerFactory.getLogger(CsvCustomerParser.class);
    private static final int CUSTOMER_ID_INDEX = 0;
    private static final int CUSTOMER_STATUS_INDEX = 1;

    public Customer toCustomerFrom(String customerText) {
        String[] customerPiece = customerText.split(SPACE.unit());
        String inputCustomerId = customerPiece[CUSTOMER_ID_INDEX].trim();
        String inputCustomerType = customerPiece[CUSTOMER_STATUS_INDEX].trim();
        validateDigit(inputCustomerId);
        validateString(inputCustomerType);
        long customerId = Long.parseLong(inputCustomerId);
        CustomerStatus findCustomerStatus = CustomerStatus.of(inputCustomerType);
        return new Customer(customerId, findCustomerStatus);
    }

    private void validateDigit(String inputCustomer) {
        if (!inputCustomer.chars().allMatch(Character::isDigit)) {
            logger.error("Fail - {}", String.format(CSV_CUSTOMER_PARSER_EXCEPTION.getMessage(), inputCustomer));
            throw new IllegalArgumentException(String.format(CSV_CUSTOMER_PARSER_EXCEPTION.getMessage(), inputCustomer));
        }
    }

    private void validateString(String inputCustomer) {
        if (!inputCustomer.chars().allMatch(Character::isAlphabetic)) {
            logger.error("Fail - {}", String.format(CSV_CUSTOMER_PARSER_EXCEPTION.getMessage(), inputCustomer));
            throw new IllegalArgumentException(String.format(CSV_CUSTOMER_PARSER_EXCEPTION.getMessage(), inputCustomer));
        }
    }
}
