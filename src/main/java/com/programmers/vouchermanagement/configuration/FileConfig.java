package com.programmers.vouchermanagement.configuration;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.configuration.profiles.FileEnabledCondition;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.util.JSONFileManager;
import com.programmers.vouchermanagement.voucher.domain.Voucher;

@Configuration
@Conditional(FileEnabledCondition.class)
public class FileConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean("customer")
    public JSONFileManager<UUID, Customer> customerJSONFileManager(ObjectMapper objectMapper) {
        return new JSONFileManager<>(objectMapper, Customer.class);
    }

    @Bean("voucher")
    JSONFileManager<UUID, Voucher> voucherJSONFileManager(ObjectMapper objectMapper) {
        return new JSONFileManager<>(objectMapper, Voucher.class);
    }
}
