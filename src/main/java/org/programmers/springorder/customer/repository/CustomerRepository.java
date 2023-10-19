package org.programmers.springorder.customer.repository;

import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.voucher.model.Voucher;
import org.programmers.springorder.voucher.model.VoucherType;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public interface CustomerRepository {
    List<Customer> findAllBlackList();
    List<Customer> findAll();
}
