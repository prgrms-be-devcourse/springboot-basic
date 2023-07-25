package com.wonu606.vouchermanager.controller.customer;

import com.wonu606.vouchermanager.controller.customer.converter.CustomerControllerConverterManager;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.OwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletRegisterRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import com.wonu606.vouchermanager.controller.customer.response.OwnedVoucherResponse;
import com.wonu606.vouchermanager.service.customer.CustomerService;
import com.wonu606.vouchermanager.service.customer.param.CustomerCreateParam;
import com.wonu606.vouchermanager.service.customer.param.WalletRegisterParam;
import com.wonu606.vouchermanager.service.customer.result.CustomerResult;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletDeleteParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerControllerConverterManager converterManager;

    public CustomerController(CustomerService service) {
        this.service = service;
        converterManager = new CustomerControllerConverterManager();
    }

    @GetMapping("/create")
    public String createCustomerForm() {
        return "customers/create-form";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute CustomerCreateRequest request) {
        CustomerCreateParam param = converterManager.convert(request, CustomerCreateParam.class);
        service.createCustomer(param);

        return "redirect:/customers/list";
    }

    @GetMapping("/list")
    public String getCustomerList(Model model) {
        List<CustomerResult> results = service.getCustomerList();

        List<CustomerResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, CustomerResponse.class))
                .collect(Collectors.toList());

        model.addAttribute("responses", responses);
        return "customers/list";
    }

    @GetMapping("/owned-vouchers")
    public String getOwnedVouchersByCustomerForm() {
        return "customers/owned-vouchers-form";
    }

    @PostMapping("/owned-vouchers")
    public String getOwnedVouchersByCustomer(@ModelAttribute OwnedVouchersRequest request,
            Model model) {
        OwnedVouchersParam param = converterManager.convert(request, OwnedVouchersParam.class);
        List<OwnedVoucherResult> results = service.findOwnedVouchersByCustomer(param);

        List<OwnedVoucherResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedVoucherResponse.class))
                .collect(Collectors.toList());

        model.addAttribute("responses", responses);
        model.addAttribute("customerId", request.getCustomerId()); // Add customer ID to the model

        return "/customers/owned-vouchers-list";
    }

    @PostMapping("/wallet/delete")
    public String deleteWallet(@ModelAttribute WalletDeleteRequest request) {
        WalletDeleteParam param = converterManager.convert(request, WalletDeleteParam.class);
        service.deleteWallet(param);

        return "redirect:/customers/owned-vouchers";
    }

    @PostMapping("/wallet/register")
    public String registerToWallet(@ModelAttribute WalletRegisterRequest request) {
        WalletRegisterParam param = converterManager.convert(request, WalletRegisterParam.class);
        service.registerToWallet(param);

        return "redirect:/customers/owned-vouchers"; // redirect to the owned vouchers page after registration
    }
}
