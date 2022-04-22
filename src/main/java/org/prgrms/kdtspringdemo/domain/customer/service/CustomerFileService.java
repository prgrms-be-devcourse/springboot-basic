package org.prgrms.kdtspringdemo.domain.customer.service;

import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.UUID;

@Service
public class CustomerFileService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private Scanner scanner = new Scanner(System.in);

    @Autowired
    ResourceLoader resourceLoader;

    public CustomerFileService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void readBlackListCSV(){
        Resource resource = resourceLoader.getResource("classpath:BLACKLIST.csv");

        try(InputStream inStream = resource.getInputStream();){
            String content = StreamUtils.copyToString(inStream, StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            logger.error("BlackList CSV file load 중에 IOException 발생");
        }
    }

    public void registerCustomer(){
        System.out.println("Write name : ");
        String name = scanner.nextLine();

        System.out.println("Write email : ");
        String email = scanner.nextLine();

        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerRepository.insert(customer);
        logger.info("회원 1명이 Customer 데이터베이스에 새로 등록되었습니다.");
    }
}
