package com.programmers.part1.customer.repository;

import com.programmers.part1.domain.VoucherWallet;
import com.programmers.part1.domain.customer.CustomerDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Repository
public class FileCustomerRepository implements CustomerRepository<UUID, CustomerDto>{

    private static final String PATH = "customer_blacklist.csv";
    ResourceLoader resourceLoader;

    public FileCustomerRepository(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public List<CustomerDto> findAllBlackCustomer() throws IOException {
        Resource resource = this.resourceLoader.getResource(PATH);
        Reader reader = new InputStreamReader(resource.getInputStream(),StandardCharsets.UTF_8);

        //회원 record
        String[] rows = FileCopyUtils.copyToString(reader).split("\n");

        List<CustomerDto> blackCustomers = new ArrayList<>();
        for(String row : rows){
            String[] column = row.split(",");
            if(column[2].equals("Black")) // filter 블랙리스트 멤버만
                blackCustomers.add(new CustomerDto(Long.parseLong(column[0]),column[1],column[2]));

        }

        return blackCustomers;
    }

    @Override
    public CustomerDto save(CustomerDto customer) {
        return null;
    }

    @Override
    public Optional<CustomerDto> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public List<CustomerDto> findAllCustomer() {
        return null;
    }

    @Override
    public List<CustomerDto> findCustomerByVoucherId(UUID voucherId) {
        return null;
    }

    @Override
    public CustomerDto update(CustomerDto customer) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public VoucherWallet insertVoucherWallet(VoucherWallet voucherWallet) {
        return null;
    }

    @Override
    public void deleteVoucherWalletByCustomerAndVoucherId(UUID customerId, UUID voucherId) {

    }
}
