package com.prgrms.management.customer.repository;

import com.prgrms.management.customer.domain.Customer;
import com.prgrms.management.customer.domain.CustomerType;
import com.prgrms.management.voucher.domain.FixedAmountVoucher;
import com.prgrms.management.voucher.domain.PercentAmountVoucher;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    private final String BLACK_LIST_FILE_NAME = "src/main/resources/customer_blacklist.csv";

    @Override
    public Customer insert(Customer customer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(BLACK_LIST_FILE_NAME, true))) {
            //bufferedWriter.write(voucher.getVoucherId() + "," + voucher.getAmount() + "," + voucher.getVoucherType());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer;
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        //try-with-resource
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(BLACK_LIST_FILE_NAME))) {
            String reader = "";
            while ((reader = bufferedReader.readLine()) != null) {
                String[] voucherInfo = reader.split(",");
                VoucherType type = VoucherType.of(voucherInfo[2]);
                if (type.equals(CustomerType.BLACKLIST)) customerList.add(new Customer()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerList;
    }
}
