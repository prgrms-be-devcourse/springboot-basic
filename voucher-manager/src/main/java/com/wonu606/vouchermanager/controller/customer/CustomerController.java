package com.wonu606.vouchermanager.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonu606.vouchermanager.controller.customer.converter.CustomerControllerConverterManager;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletRegisterRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerCreateResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import com.wonu606.vouchermanager.controller.customer.response.OwnedVoucherResponse;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerCreateResult;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.io.IOException;
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

    public String createCustomer(CustomerCreateRequest request) {
        CustomerCreateParam param = converterManager.convert(request, CustomerCreateParam.class);
        CustomerCreateResult result = service.createCustomer(param);

        CustomerCreateResponse response = converterManager.convert(result,
                CustomerCreateResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCustomerList() {
        List<CustomerResult> results = service.getCustomerList();

        List<CustomerResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, CustomerResponse.class))
                .collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(responses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOwnedVouchersByCustomer(OwnedVouchersRequest request) {
        OwnedVouchersParam param = converterManager.convert(request, OwnedVouchersParam.class);
        List<OwnedVoucherResult> results = service.findOwnedVouchersByCustomer(param);

        List<OwnedVoucherResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedVoucherResponse.class))
                .collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(responses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWallet(WalletDeleteRequest request) {
        WalletDeleteParam param = converterManager.convert(request, WalletDeleteParam.class);
        service.deleteWallet(param);
    }

    public void registerToWallet(WalletRegisterRequest request) {
        WalletRegisterParam param = converterManager.convert(request, WalletRegisterParam.class);
        service.registerToWallet(param);
    }
}
