//package com.example.springbootbasic.repository;
//
//import com.example.springbootbasic.config.AppConfiguration;
//import com.example.springbootbasic.domain.customer.Customer;
//import com.example.springbootbasic.parser.CsvCustomerParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//@Profile("dev")
//public class CsvCustomerRepository implements CustomerRepository {
//    private final AppConfiguration appConfiguration;
//    private final CsvCustomerParser csvParser = new CsvCustomerParser();
//
//    @Autowired
//    public CsvCustomerRepository(AppConfiguration appConfiguration) {
//        this.appConfiguration = appConfiguration;
//    }
//    @Override
//    public List<Customer> findAllCustomers() {
//        Path csvPath = Paths.get(appConfiguration.getCustomerCsvResource());
//        try {
//            List<String> voucherTexts = Files.readAllLines(csvPath);
//            return voucherTexts.stream()
//                    .map(csvParser::parseToCustomerFrom)
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
