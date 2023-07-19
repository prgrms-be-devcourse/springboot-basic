package com.wonu606.vouchermanager.service.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.CustomerRepository;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.converter.CustomerCreateQueryConverter;
import com.wonu606.vouchermanager.service.customer.converter.CustomerCreateResultConverter;
import com.wonu606.vouchermanager.service.customer.converter.CustomerResultConverter;
import com.wonu606.vouchermanager.service.customer.creator.CustomerCreator;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerService {

    private final VoucherWalletService voucherWalletService;

    private final CustomerRepository repository;

    private final CustomerCreator customerCreator;
    private final CustomerCreateQueryConverter customerCreateQueryConverter;
    private final CustomerCreateResultConverter customerCreateResultConverter;
    private final CustomerResultConverter customerResultConverter;

    public CustomerService(VoucherWalletService voucherWalletService,
            CustomerRepository repository) {
        this.voucherWalletService = voucherWalletService;
        this.repository = repository;

        customerCreator = new CustomerCreator();
        customerCreateQueryConverter = new CustomerCreateQueryConverter();
        customerCreateResultConverter = new CustomerCreateResultConverter();
        customerResultConverter = new CustomerResultConverter();
    }

    public CustomerCreateResult createCustomer(CustomerCreateParam param) {
        Customer createdCustomer = customerCreator.create(param);
        CustomerCreateQuery query = customerCreateQueryConverter.convert(createdCustomer);

        CustomerCreateResultSet resultSet = repository.insert(query);
        return customerCreateResultConverter.convert(resultSet);
    }

    public List<CustomerResult> getCustomerList() {
        List<CustomerResultSet> resultSets = repository.findAll();

        return resultSets.stream()
                .map(customerResultConverter::convert)
                .collect(Collectors.toList());
    }

    public List<OwnedVoucherResult> findOwnedVouchersByCustomer(OwnedVoucherParam param) {
        return voucherWalletService.findOwnedVouchersByCustomer(param);
    }

    public void deleteWallet(WalletDeleteParam param) {
        voucherWalletService.deleteWallet(param);
    }
}
