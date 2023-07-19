package com.wonu606.vouchermanager.service.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.CustomerRepository;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.creator.CustomerCreator;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerService {

    private final VoucherWalletService voucherWalletService;

    private final CustomerRepository repository;

    private final CustomerCreator customerCreator;

    private final CustomerResultSetustomerCreateResultConverter customerCreateResultConverter;
    private final CustomerListGetResultConverter customerListGetResultConverter;

    public CustomerService(VoucherWalletService voucherWalletService, CustomerRepository repository) {
        this.voucherWalletService = voucherWalletService;
        this.repository = repository;
        this.customerCreator = new CustomerCreator();
        this.customerCreateResultConverter;
        this.customerListGetResultConverter;
    }

    public CustomerCreateResult createCustomer(CustomerCreateParam param) {
        Customer createdCustomer = customerCreator.create(param);
        CustomerCreateQuery query = customerQueryConverter.convert(createdCustomer);

        CustomerCreateResultSet resultSet = repository.insert(query);
        return customerCreateResultConverter.convert(resultSet);
    }

    public List<CustomerResult> getCustomerList() {
        List<CustomerResultSet> resultSet = repository.findAll();
        return customerListGetResultConverter.convert(resultSet);
    }

    public OwnedVouchersResult findOwnedVouchersByCustomer(OwnedVoucherParam param) {
        return voucherWalletService.findOwnedVouchersByCustomer(param);
    }

    public void deleteWallet(WalletDeleteParam param) {
        voucherWalletService.deleteWallet(param);
    }
}
