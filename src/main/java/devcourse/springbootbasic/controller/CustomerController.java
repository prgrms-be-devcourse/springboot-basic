package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.service.CustomerService;
import devcourse.springbootbasic.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final WalletService walletService;

    public List<CustomerFindResponse> findAllBlacklistedCustomers() {
        return this.customerService.findAllBlacklistedCustomers();
    }

    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {
        return new CustomerResponse(this.customerService.createCustomer(customerCreateRequest));
    }

    public CustomerResponse updateBlacklistStatus(CustomerUpdateBlacklistRequest customerUpdateBlacklistRequest) {
        return new CustomerResponse(this.customerService.updateBlacklistStatus(customerUpdateBlacklistRequest));
    }

    public List<VoucherFindResponse> findVouchersByCustomerId(UUID customerId) {
        return this.walletService.findVouchersByCustomerId(customerId);
    }
}
