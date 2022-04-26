package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.AppProperties;
import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private Map<UUID, Customer> customerMap = new ConcurrentHashMap<>();
    private List<Customer> customerBlackList = new ArrayList<>();

    public FileCustomerRepository(AppProperties appProperties) {
        try {
            loadFile(appProperties.getCustomer_blacklist_info());
        }
        catch (Exception e) {
            //no need to throw exception (it can use empty array list)
        }
    }

    private void loadFile(String fileName) throws Exception {
        int i = 0;
        Scanner sc = new Scanner(new File(fileName));

        try {
            while (sc.hasNext()) {
                String str = sc.nextLine();
                ArrayList<String> params = Arrays.stream(str.split(",")).collect(toCollection(ArrayList::new));
                String name = params.get(0);
                String age = params.get(1);
                String sex = params.get(2);
                customerBlackList.add(new Customer(name, age, sex));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            sc.close();
            throw new WrongCustomerParamsException();
        }
        sc.close();
    }

    @Override
    public List<Customer> getAllBlacklist() {
        return customerBlackList;
    }

    @Override
    public List<Customer> getAll() {
        return customerMap.values().stream().toList();
    }

    @Override
    public Optional<Customer> get(String id) {
        return Optional.ofNullable(customerMap.get(UUID.fromString(id)));
    }

    @Override
    public void add(Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }
}
