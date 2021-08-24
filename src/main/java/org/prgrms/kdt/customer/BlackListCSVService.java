package org.prgrms.kdt.customer;

import java.util.UUID;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.file.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 11:43 오후
 */
@Service
public class BlackListCSVService {

    @PostConstruct
    public void initBlackList() {
        System.out.println("=== black list ===");
        var list = FileUtil.readCSV(new ClassPathResource("customer_blackList.csv"));
        list.stream()
                .map(name -> new Customer(UUID.randomUUID(), name, CustomerType.BLACKLIST))
                .forEach(System.out::println);
    }

}
