package team.marco.voucher_management_system.controller.customer;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.controller.customer.dto.CustomerIdAndName;
import team.marco.voucher_management_system.controller.customer.dto.CustomerResponse;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.service.customer.BlacklistService;
import team.marco.voucher_management_system.service.customer.CustomerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final BlacklistService blacklistService;

    public CustomerController(CustomerService customerService, BlacklistService blacklistService) {
        this.customerService = customerService;
        this.blacklistService = blacklistService;
    }

    public List<CustomerIdAndName> findBlacklist() {
        return blacklistService.getBlacklist();
    }

    public boolean isExistCustomer(String customerId) {
        try {
            customerService.findCustomerById(UUID.fromString(customerId));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<CustomerResponse> findAll() {
        return customerService.findAllCustomer().stream()
                .map(CustomerResponse::of)
                .toList();
    }

    public Customer findCustomerByEmail(String email) {
        Customer customer = customerService.findCustomerByEmail(email);
        return customer;
    }
}
