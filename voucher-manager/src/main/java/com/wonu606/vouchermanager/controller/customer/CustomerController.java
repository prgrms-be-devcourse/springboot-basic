package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerGetOwnedVoucherResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerGetResponseConverter;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.CustomerGetOwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetOwnedVouchersResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetResponse;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService service;

    private final CustomerCreateResponseConverter customerCreateResponseConverter;
    private final CustomerGetResponseConverter customerGetResponseConverter;
    private final CustomerGetOwnedVoucherResponseConverter customerGetOwnedVouchersResponseConverter;

    public CustomerController(CustomerService service,
            CustomerCreateResponseConverter customerCreateResponseConverter,
            CustomerGetResponseConverter customerGetResponseConverter,
            CustomerGetOwnedVoucherResponseConverter customerGetOwnedVouchersResponseConverter) {
        this.service = service;
        this.customerCreateResponseConverter = customerCreateResponseConverter;
        this.customerGetResponseConverter = customerGetResponseConverter;
        this.customerGetOwnedVouchersResponseConverter = customerGetOwnedVouchersResponseConverter;
    }

    public CustomerCreateResponse createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = createCustomerCreateParam(request);
        CustomerCreateResult result = service.createCustomer(param);

        return customerCreateResponseConverter.convert(result);
    }

    public CustomerGetResponse getCustomerList() {
        CustomerGetResult result = service.getCustomerList();

        return customerGetResponseConverter.convert(result);
    }

    public CustomerGetOwnedVouchersResponse getOwnedVouchersByCustomer(
            CustomerGetOwnedVouchersRequest request) {
        CustomerGetOwnedVoucherParam param = createCustomerGetOwnedVoucherParam(request);
        CustomerGetOwnedVoucherResult result = service.findOwnedVouchersByCustomer(param);

        return customerGetOwnedVouchersResponseConverter.convert(result);
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = createWalletDeleteParam(request);
        service.deleteWallet(param);
    }

    private CustomerCreateParam createCustomerCreateParam(CustomerCreateRequest request) {

    }

    private CustomerGetOwnedVoucherParam createCustomerGetOwnedVoucherParam(
            CustomerGetOwnedVouchersRequest request) {

    }

    private WalletDeleteParam createWalletDeleteParam(WalletDeleteRequest request) {

    }
}
