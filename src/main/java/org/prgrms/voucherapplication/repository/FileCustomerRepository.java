package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.configutarion.FilePathConfiguration;
import org.prgrms.voucherapplication.entity.BlackListCustomer;
import org.prgrms.voucherapplication.entity.Customer;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 고객 데이터를 파일로 관리하는 레포지터리
 */
@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final FilePathConfiguration configuration;

    public FileCustomerRepository(FilePathConfiguration configuration) {
        this.configuration = configuration;
    }


    /**
     * 블랙리스트에 있는 모든 고객을 반환
     *
     * @return List 형태로 Customer
     * @throws IOException
     */
    @Override
    public List<Customer> findAll() throws IOException {
        File file = new File(configuration.getBlacklist());
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<Customer> customerList = new ArrayList<>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] customerInfo = line.split(",");
            customerList.add(restoreCustomer(customerInfo[0], customerInfo[1]));
        }
        reader.close();
        return customerList;
    }

    /**
     * string type의 고객 id, 고객 이름을 가지고 Customer 객체를 반환
     *
     * @param id
     * @param customerName
     * @return 파라미터에 따른 Customer 객체
     */
    private Customer restoreCustomer(String id, String customerName) {
        return new BlackListCustomer(Long.valueOf(id), customerName);
    }
}
