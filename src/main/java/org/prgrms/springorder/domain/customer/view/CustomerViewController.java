package org.prgrms.springorder.domain.customer.view;

import java.util.List;
import java.util.UUID;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.domain.customer.Wallet;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.service.CustomerService;
import org.prgrms.springorder.domain.voucher.api.VoucherIdRequest;
import org.prgrms.springorder.domain.voucher.api.request.AllocateVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.springframework.boot.Banner.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(
        CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/assign")
    public String allocateVoucher(
        @RequestParam(required = false) String customerId,
        @RequestParam(required = false) String voucherId,
        Model model) {

        model.addAttribute("customerId", customerId);
        model.addAttribute("voucherId", voucherId);

        return "/assign-voucher";
    }

    @PostMapping("/customers/assign")
    public String allocateVoucher(AllocateVoucherRequest request) {
        customerService.allocateVoucher(request.getCustomerId(), request.getVoucherId());

        return "redirect:/vouchers";
    }

    @GetMapping("/customers")
    public String allCustomers(Model model) {
        List<Customer> customers = customerService.findAllCustomers();

        model.addAttribute("customers", customers);

        return "/customers";
    }

    @GetMapping("/block-customers")
    public String blockCustomers(Model model) {
        List<BlockCustomer> blockCustomers = customerService.findAllBlockCustomers();

        model.addAttribute("blockCustomers", blockCustomers);

        return "/block-customers";
    }

    @GetMapping("/customers/{customerId}/wallet")
    public String getCustomerWallet(@PathVariable UUID customerId, Model model) {

        Wallet wallet = customerService.findAllVouchers(customerId);

        model.addAttribute("customer", wallet.getCustomer());
        model.addAttribute("vouchers", wallet.getVouchers());

        return "/customer-wallet";
    }

    @PostMapping("/customers/{customerId}/vouchers/delete")
    public String deleteVoucher(@PathVariable UUID customerId, VoucherIdRequest request) {
        customerService.deleteVoucher(customerId, request.getVoucherId());
        return "redirect:/customers/" + customerId.toString() + "/wallet";
    }

}
