package org.prgrms.kdt.customer.repository;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CsvBlackListRepository implements CustomerRepository{

    private List<Customer> customerList = new ArrayList<>();
    private File file;

    @Value("${csv.customer-blacklist.file-path}")
    private String filePath;

    @Override
    public void insert(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerList;
    }

    @PostConstruct
    public void loadCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        file = resource.getFile();
        try(BufferedReader br = new BufferedReader(new FileReader(file));){
            String row = "";
            while((row = br.readLine()) != null){
                insert(new Customer(row));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
