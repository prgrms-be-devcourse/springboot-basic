package org.prgrms.kdt.customer;

import lombok.NonNull;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    @NonNull
    private static final String PATH = "customer-blacklist.csv";
    private final File file = new File(PATH);
    private List<Customer> blackList = new ArrayList<>();

    @PostConstruct
    public void loadBlackList() throws IOException {
        if (file.exists() == false)
            file.createNewFile();

        var br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            var st = new StringTokenizer(line, ",");

            var id = UUID.fromString(st.nextToken());
            var customerId = st.nextToken();
            var email = st.nextToken();

            var blackListCustomer = new Customer(id, customerId, email);
            blackList.add(blackListCustomer);
        }
        br.close();

        System.out.println("FileCustomerRepository created!");
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        //TODO: 구현 예정(미션4와 무관)
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByUserId(String customerId) {
        //TODO: 구현 예정(미션4와 무관)
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        //TODO: 구현 예정(미션4와 무관)
        return null;
    }

    @Override
    public List<Customer> getBlackList() {
        return blackList;
    }
}
