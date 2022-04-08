package com.example.demo.voucher;

import com.example.demo.customer.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan(basePackageClasses = {Voucher.class, Customer.class})
public class VoucherContext {
}
