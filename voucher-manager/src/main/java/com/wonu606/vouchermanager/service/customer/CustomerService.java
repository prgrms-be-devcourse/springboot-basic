package com.wonu606.vouchermanager.service.customer;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.repository.customer.CustomerRepository;
import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.service.customer.converter.CustomerServiceConverterManager;
import com.wonu606.vouchermanager.service.customer.factory.CustomerFactory;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
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
    private final CustomerFactory customerFactory;
    private final CustomerServiceConverterManager converterManager;

    public CustomerService(VoucherWalletService voucherWalletService, CustomerRepository repository,
            CustomerFactory customerFactory, CustomerServiceConverterManager converterManager) {
        this.voucherWalletService = voucherWalletService;
        this.repository = repository;
        this.customerFactory = customerFactory;
        this.converterManager = converterManager;
    }

    public CustomerCreateResult createCustomer(CustomerCreateParam param) {
        Customer createdCustomer = customerFactory.create(param);
        CustomerCreateQuery query = converterManager.convert(createdCustomer,
                CustomerCreateQuery.class);

        CustomerCreateResultSet resultSet = repository.insert(query);
        return converterManager.convert(resultSet, CustomerCreateResult.class);
    }

    public List<CustomerResult> getCustomerList() {
        List<CustomerResultSet> resultSets = repository.findAll();

        return resultSets.stream()
                .map(rs -> converterManager.convert(rs, CustomerResult.class))
                .collect(Collectors.toList());
    }

    public List<OwnedVoucherResult> findOwnedVouchersByCustomer(OwnedVouchersParam param) {
        return voucherWalletService.findOwnedVouchersByCustomer(param);
    }

    public void deleteWallet(WalletDeleteParam param) {
        voucherWalletService.deleteWallet(param);
    }

    public void registerToWallet(WalletRegisterParam param) {
        voucherWalletService.registerToWallet(param);
    }
}
