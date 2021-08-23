package org.programmers.customer.repository;

import org.programmers.customer.model.Customer;
import org.programmers.voucher.model.FixedAmountVoucher;
import org.programmers.voucher.model.PercentDiscountVoucher;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileCustomerRepository implements CustomerRepository {
    private final Map<Integer, Customer> blackListMap = new ConcurrentHashMap<>();

    private final String CustomerfilePath = "C:\\Users\\skyey\\Desktop\\";
    private final String CustomerfileName = "customer_blackList.csv";
    File Customerfile = new File(CustomerfilePath + CustomerfileName);

    @Override
    public List<Customer> findAllBlackList() {
        return new ArrayList<>(blackListMap.values());
    }

    @Override
    public void save(Customer customer) {
        blackListMap.put(customer.getCustomerId(), customer);
    }

    @PostConstruct
    public void loadBlackList() {
        try {
            BufferedReader blackListBr = new BufferedReader(new FileReader(Customerfile));
            String loadLine;

            while ((loadLine = blackListBr.readLine()) != null) {
                String[] loadLineArr = loadLine.split(",");
                int customerId = Integer.parseInt((loadLineArr[0]));
                String name = loadLineArr[1];

                save(new Customer(customerId, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
