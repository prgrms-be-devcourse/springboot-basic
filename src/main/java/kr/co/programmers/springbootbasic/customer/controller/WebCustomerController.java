package kr.co.programmers.springbootbasic.customer.controller;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.service.BlackCustomerService;
import kr.co.programmers.springbootbasic.customer.service.NormalCustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Controller
@Profile("web")
@RequestMapping("/customers")
public class WebCustomerController {
    private final NormalCustomerService jdbcCustomerService;
    private final BlackCustomerService blackCustomerService;

    public WebCustomerController(NormalCustomerService jdbcCustomerService,
                                 BlackCustomerService blackCustomerService) {
        this.jdbcCustomerService = jdbcCustomerService;
        this.blackCustomerService = blackCustomerService;
    }

    @GetMapping("/home")
    public String loadHomePage() {
        return "customerTemplate/home";
    }

    @GetMapping("/customer")
    public String loadCustomerCreatePage() {
        return "customerTemplate/create";
    }

    @PostMapping("/customer")
    public String createCustomer(@RequestParam("customerName") String customerName,
                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/customers/customer");

        CustomerResponse customerResponse = jdbcCustomerService.createCustomer(customerName);
        String message = customerResponse.formatCustomerResponseDto();
        redirectAttributes.addFlashAttribute("message", message);


        return "redirect:/customers/success";
    }

    @GetMapping("/list")
    public String loadListHomePage() {
        return "customerTemplate/list";
    }

    @PostMapping("/list/customer")
    public String findByCustomerId(@RequestParam("uuid") String customerId,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/customers/list");

        Optional<CustomerResponse> customerResponse = jdbcCustomerService.findByCustomerId(customerId);
        String message = customerResponse.map(CustomerResponse::formatCustomerResponseDto)
                .orElseGet(() -> MessageFormat.format("유저 아이디 : {0}를 가진 유저가 존재하지 않습니다.",
                        customerId));
        redirectAttributes.addFlashAttribute("message", message);


        return "redirect:/customers/success";
    }

    @PostMapping("/list/voucher")
    public String findByVoucherId(@RequestParam("uuid") String voucherId,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/customers/list/voucher");

        Optional<CustomerResponse> customerResponse = jdbcCustomerService.findByVoucherId(voucherId);
        String message = customerResponse.map(CustomerResponse::formatCustomerResponseDto)
                .orElseGet(() -> MessageFormat.format("바우처 아이디 : {0}를 가진 유저가 존재하지 않습니다.",
                        voucherId));
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/customers/success";
    }

    @GetMapping("/list/all")
    public String loadListAllVouchersPage(Model model) {
        List<CustomerResponse> allCustomer = jdbcCustomerService.findAllCustomer();
        List<CustomerResponse> allBlackCustomer = blackCustomerService.findAllBlackCustomer();

        model.addAttribute("normalCustomers", allCustomer);
        model.addAttribute("blackCustomers", allBlackCustomer);

        return "customerTemplate/listAll";
    }

    @GetMapping("/update")
    public String loadUpdateCustomerPage() {
        return "customerTemplate/update";
    }

    @PutMapping("/update")
    public String updateCustomerByCustomerId(@RequestParam("customerId") String customerId,
                                             @RequestParam("customerStatus") CustomerStatus customerStatus,
                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("redirectUrl", "/customers/update");

        CustomerResponse customerResponse = jdbcCustomerService.updateCustomer(customerId, customerStatus);
        String message = customerResponse.formatCustomerResponseDto();
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/customers/success";
    }

    @GetMapping("/success")
    public String loadServiceSuccessPage() {
        return "success";
    }

    @GetMapping("/fail")
    public String loadServiceFailPage() {
        return "fail";
    }
}
