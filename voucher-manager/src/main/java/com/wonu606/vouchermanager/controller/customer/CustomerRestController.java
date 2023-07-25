package com.wonu606.vouchermanager.controller.customer;

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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRestController {

    private final CustomerService service;
    private final CustomerControllerConverterManager converterManager;

    public CustomerRestController(CustomerService service) {
        this.service = service;
        converterManager = new CustomerControllerConverterManager();
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerCreateResponse> createCustomer(
            @RequestBody CustomerCreateRequest request) {
        CustomerCreateParam param = converterManager.convert(request, CustomerCreateParam.class);
        CustomerCreateResult result = service.createCustomer(param);

        CustomerCreateResponse response = converterManager.convert(result,
                CustomerCreateResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerResponse>> getCustomerList() {
        List<CustomerResult> results = service.getCustomerList();
        List<CustomerResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, CustomerResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/owned-vouchers")
    public ResponseEntity<List<OwnedVoucherResponse>> getOwnedVouchersByCustomer(
            @RequestBody OwnedVouchersRequest request) {
        OwnedVouchersParam param = converterManager.convert(request, OwnedVouchersParam.class);
        List<OwnedVoucherResult> results = service.findOwnedVouchersByCustomer(param);

        List<OwnedVoucherResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedVoucherResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/wallet/delete")
    public ResponseEntity<Void> deleteWallet(@RequestBody WalletDeleteRequest request) {
        WalletDeleteParam param = converterManager.convert(request, WalletDeleteParam.class);
        service.deleteWallet(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/wallet/register")
    public ResponseEntity<Void> registerToWallet(@RequestBody WalletRegisterRequest request) {
        WalletRegisterParam param = converterManager.convert(request, WalletRegisterParam.class);
        service.registerToWallet(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
