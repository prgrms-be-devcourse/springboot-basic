package com.devcourse.springbootbasic.application.global.utils;

import com.devcourse.springbootbasic.application.customer.model.Customer;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utils {

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static Customer convertCsvToCustomer(String blackCustomerInfo) {
        String[] customerInfoArray = blackCustomerInfo.split(",");
        return new Customer(
                UUID.fromString(customerInfoArray[0]),
                customerInfoArray[1],
                customerInfoArray[2],
                LocalDateTime.parse(customerInfoArray[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn"))
        );
    }

}
