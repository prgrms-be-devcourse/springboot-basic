package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerCreateResponseConverter;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerGetOwnedVoucherResponseConverter;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.CustomerGetOwnedVoucherRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetOwnedVoucherResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetResponse;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CustomerController {

    private final CustomerService service;

    private final CustomerCreateResponseConverter customerCreateResponseConverter;
    private final CustomerGetResponseConverter customerGetResponseConverter;
    private final CustomerGetOwnedVoucherResponseConverter customerGetOwnedVoucherResponseConverter;

    public CustomerController(CustomerService service, VoucherService voucherService,
            VoucherWalletService walletService) {
        this.service = service;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    public Customer createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = createCustomerCreateParam(request);
        CustomerCreateResult result = service.createCustomer(param);

        CustomerCreateResponse response = customerCreateResponseConverter.convert(result);
        return service.saveCustomer(response);
    }

    public List<CustomerGetResponse> getCustomerList() {
        List<CustomerGetResult> results = service.getCustomerList();
        List<CustomerGetResponse> responses = results.stream()
                .map(customerGetResponseConverter::convert)
                .collect(Collectors.toList());
        return responses;
    }

    public List<Voucher> getOwnedVouchersByCustomer(CustomerGetOwnedVoucherRequest request) {
        CustomerGetOwnedVoucherParam param = createCustomerGetOwnedVoucherParam(request);
        List<CustomerGetOwnedVoucherResult> results = service.findOwnedVoucherByCustomer(param);

        List<CustomerGetOwnedVoucherResponse> responses = results.stream()
                .map(customerGetOwnedVoucherResponseConverter::convert)
                .collect(Collectors.toList());
        return responses;
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = createWalletDeleteParam(request);
        service.deleteWallet(param);
    }

    private CustomerCreateParam createCustomerCreateParam(CustomerCreateRequest request) {

    }

    private CustomerGetOwnedVoucherParam createCustomerGetOwnedVoucherParam(
            CustomerGetOwnedVoucherRequest request) {

    }

    private WalletDeleteParam createWalletDeleteParam(WalletDeleteRequest request) {

    }
}
