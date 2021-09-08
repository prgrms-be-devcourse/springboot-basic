package org.prgrms.kdt.assignments;

import org.prgrms.kdt.customer.Customer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BlackListCSVFileService {

    private static final String blackListFile = "BlackList.csv";

    @PostConstruct
    public void readBlakcList() throws IOException {
        System.out.println("****** BlackList ******");
        var blackList = FileUtil.readCSV(blackListFile);
        blackList.keySet().stream()
                .map(email -> {
                    var customer = new Customer(UUID.randomUUID(), blackList.get(email), email, LocalDateTime.now());
                    System.out.println("Customer ID : "+customer.getCustomerId() + " / Customer Name : " + customer.getName() + " / Customer Email : " + customer.getEmail());
                    return customer;
                })
                .forEach(System.out::println);
    }
}
