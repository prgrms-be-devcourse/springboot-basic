package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerControllerConverterManager;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import com.wonu606.vouchermanager.controller.customer.response.OwnedVoucherResponse;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService service;
    private final CustomerControllerConverterManager converterManager;

    public CustomerController(CustomerService service) {
        this.service = service;
        converterManager = new CustomerControllerConverterManager();
    }

    public CustomerCreateResponse createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = converterManager.convert(request, CustomerCreateParam.class);
        CustomerCreateResult result = service.createCustomer(param);

        return converterManager.convert(result, CustomerCreateResponse.class);
    }

    public List<CustomerResponse> getCustomerList() {
        List<CustomerResult> results = service.getCustomerList();

        return results.stream()
                .map(rs -> converterManager.convert(rs, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    public List<OwnedVoucherResponse> getOwnedVouchersByCustomer(OwnedVouchersRequest request) {
        OwnedVouchersParam param = converterManager.convert(request, OwnedVouchersParam.class);
        List<OwnedVoucherResult> results = service.findOwnedVouchersByCustomer(param);

        return results.stream()
                .map(rs -> converterManager.convert(rs, OwnedVoucherResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = converterManager.convert(request, WalletDeleteParam.class);
        service.deleteWallet(param);
    }
}
