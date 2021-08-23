package org.prgrms.kdt.customer;

import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.file.FileUtil;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 11:43 오후
 */
@Service
public class BlackListCSVService {

    @PostConstruct
    public void initBlackList() {
        List<String> blackList = FileUtil.readCSV("customer_blackList.csv");

        System.out.println("=== Black List ===");
        blackList.stream()
                .map(name -> new Customer(UUID.randomUUID(), name, CustomerType.BLACKLIST))
                .forEach(System.out::println);
        System.out.println();
    }

}
