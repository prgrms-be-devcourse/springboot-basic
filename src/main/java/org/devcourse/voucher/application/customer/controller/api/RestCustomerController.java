package org.devcourse.voucher.application.customer.controller.api;

import org.devcourse.voucher.application.customer.controller.dto.CustomerRequest;
import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Customer;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.devcourse.voucher.core.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/customer")
public class RestCustomerController {

    private final CustomerService customerService;

    public RestCustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public ApiResponse<CustomerResponse> postCreateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customer = customerService.createCustomer(
                customerRequest.getName(),
                new Email(customerRequest.getEmail())
        );
        return ApiResponse.ok(CREATED, customer);
    }

    @GetMapping("")
    public ApiResponse<List<CustomerResponse>> getCustomerList(Pageable pageable) {
        List<CustomerResponse> customers = customerService.recallAllCustomer(pageable);
        return ApiResponse.ok(OK, customers);
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.recallCustomerById(UUID.fromString(id));
        return ApiResponse.ok(OK, customer);
    }

    @PatchMapping("/{id}")
    public ApiResponse<CustomerResponse> patchUpdateCustomer(@PathVariable String id, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customer = customerService.updateCustomer(
                UUID.fromString(id),
                customerRequest.getName(),
                new Email(customerRequest.getEmail())
        );
        return ApiResponse.ok(OK, customer);
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRemoveCustomer(@PathVariable String id) {
        customerService.deleteCustomer(UUID.fromString(id));
        return ApiResponse.ok(OK, "Customer delete : " + id);
    }
}
