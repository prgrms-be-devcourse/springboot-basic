package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateParamConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerListResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.OwnedVoucherParamConverter;
import com.wonu606.vouchermanager.controller.customer.converter.OwnedVoucherResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.WalletDeleteParamConverter;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerListGetResponse;
import com.wonu606.vouchermanager.controller.customer.response.OwnedVouchersResponse;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService service;

    private final CustomerCreateParamConverter customerCreateParamConverter;
    private final OwnedVoucherParamConverter ownedVoucherParamConverter;
    private final WalletDeleteParamConverter walletDeleteParamConverter;
    private final CustomerCreateResponseConverter customerCreateResponseConverter;
    private final CustomerListResponseConverter customerListResponseConverter;
    private final OwnedVoucherResponseConverter ownedVouchersResponseConverter;

    public CustomerController(CustomerService service) {
        this.service = service;

        this.customerCreateParamConverter = new CustomerCreateParamConverter();
        this.ownedVoucherParamConverter = new OwnedVoucherParamConverter();
        this.walletDeleteParamConverter = new WalletDeleteParamConverter();
        this.customerCreateResponseConverter = new CustomerCreateResponseConverter();
        this.customerListResponseConverter = new CustomerListResponseConverter();
        this.ownedVouchersResponseConverter = new OwnedVoucherResponseConverter();
    }

    public CustomerCreateResponse createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = customerCreateParamConverter.convert(request);
        CustomerCreateResult result = service.createCustomer(param);

        return customerCreateResponseConverter.convert(result);
    }

    public CustomerListGetResponse getCustomerList() {
        CustomerListResult result = service.getCustomerList();

        return customerListResponseConverter.convert(result);
    }

    public OwnedVouchersResponse getOwnedVouchersByCustomer(OwnedVouchersRequest request) {
        OwnedVoucherParam param = ownedVoucherParamConverter.convert(request);
        OwnedVoucherResult result = service.findOwnedVouchersByCustomer(param);

        return ownedVouchersResponseConverter.convert(result);
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = walletDeleteParamConverter.convert(request);
        service.deleteWallet(param);
    }
}
