package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateParamConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.OwnedVoucherResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.OwnedVouchersParamConverter;
import com.wonu606.vouchermanager.controller.customer.converter.WalletDeleteParamConverter;
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

    private final CustomerCreateParamConverter customerCreateParamConverter;
    private final CustomerCreateResponseConverter customerCreateResponseConverter;
    private final CustomerResponseConverter customerResponseConverter;
    private final OwnedVouchersParamConverter ownedVouchersParamConverter;
    private final OwnedVoucherResponseConverter ownedVoucherResponseConverter;
    private final WalletDeleteParamConverter walletDeleteParamConverter;

    public CustomerController(CustomerService service) {
        this.service = service;

        customerCreateParamConverter = new CustomerCreateParamConverter();
        customerCreateResponseConverter = new CustomerCreateResponseConverter();
        customerResponseConverter = new CustomerResponseConverter();
        ownedVouchersParamConverter = new OwnedVouchersParamConverter();
        ownedVoucherResponseConverter = new OwnedVoucherResponseConverter();
        walletDeleteParamConverter = new WalletDeleteParamConverter();
    }

    public CustomerCreateResponse createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = customerCreateParamConverter.convert(request);
        CustomerCreateResult result = service.createCustomer(param);

        return customerCreateResponseConverter.convert(result);
    }

    public List<CustomerResponse> getCustomerList() {
        List<CustomerResult> results = service.getCustomerList();

        return results.stream()
                .map(customerResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public List<OwnedVoucherResponse> getOwnedVouchersByCustomer(OwnedVouchersRequest request) {
        OwnedVouchersParam param = ownedVouchersParamConverter.convert(request);
        List<OwnedVoucherResult> results = service.findOwnedVouchersByCustomer(param);

        return results.stream()
                .map(ownedVoucherResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = walletDeleteParamConverter.convert(request);
        service.deleteWallet(param);
    }
}
