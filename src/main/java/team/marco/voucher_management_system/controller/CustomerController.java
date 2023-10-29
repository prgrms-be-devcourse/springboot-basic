package team.marco.voucher_management_system.controller;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.service.BlacklistService;
import team.marco.voucher_management_system.service.CustomerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final BlacklistService blacklistService;

    public CustomerController(CustomerService customerService, BlacklistService blacklistService) {
        this.customerService = customerService;
        this.blacklistService = blacklistService;
    }

    public List<String> getBlacklistInfo() {
        return blacklistService.getBlacklist().stream()
                .map(dto -> format("id: {0}, 고객명: {1} ", dto.getId() , dto.getName()))
                .toList();
    }

    public boolean isExistCustomer(String customerId) {
        try {
            customerService.findCustomer(UUID.fromString(customerId));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
