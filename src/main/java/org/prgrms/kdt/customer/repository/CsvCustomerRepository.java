package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvCustomerRepository implements CustomerRepository {

    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

    //@Value는 빈 주입이 모두 끝난 후에 적용되어서 인스턴스 변수에 두면 null이 된다. -> 생성자에서 받는다.
    public CsvCustomerRepository(@Value("${data.customer.path}") String pathPrefix,
                                 @Value("${data.customer.name}") String fileName) throws FileNotFoundException {
        final String csvFilePath = pathPrefix + fileName;
        String line;
        List<String> stringCustomerList = new ArrayList<>();
        extractDataFromCsv(csvFilePath, stringCustomerList);
        convertDataIntoCustomers(stringCustomerList);
    }

    private void convertDataIntoCustomers(List<String> stringCustomerList) {
        stringCustomerList.forEach(str -> {
            String[] customerInfo = str.split(" ");
            Customer customer = new Customer(customerInfo);
            customers.put(customer.getUuid(), customer);
        });
    }

    private void extractDataFromCsv(String csvFilePath, List<String> stringCustomerList) {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath));
            while ((line = bufferedReader.readLine()) != null) {
                stringCustomerList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("파일이 존재하지 않습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일 읽/쓰기에 실패했습니다.");
        }
    }

    @Override
    public List<Customer> getCustomers() {
        return customers.values().stream().filter(customer -> !customer.isBanned()).toList();
    }

    @Override
    public List<Customer> getBlacklist() {
        return customers.values().stream().filter(Customer::isBanned).toList();
    }

    @Override
    public boolean isInBlacklist(UUID customerUuid) {
        return customers.get(customerUuid).isBanned();
    }
}
