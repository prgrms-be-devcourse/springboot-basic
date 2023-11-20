package org.prgrms.vouchermanager.handler.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.wallet.ApiWalletRequest;
import org.prgrms.vouchermanager.domain.wallet.Wallet;
import org.prgrms.vouchermanager.dto.CustomerRequest;
import org.prgrms.vouchermanager.dto.WalletRequest;
import org.prgrms.vouchermanager.exception.NotExistEmailException;
import org.prgrms.vouchermanager.service.CustomerService;
import org.prgrms.vouchermanager.service.VoucherService;
import org.prgrms.vouchermanager.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebWalletController {
    private final WalletService service;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    @GetMapping("/wallets")
    public String walletInit(){
        return "wallet/wallet-home";
    }

    @GetMapping("/wallets/create")
    public String createForm(Model model) {
        List<Voucher> vouchers = voucherService.findAllVoucher();
        List<CustomerRequest> customers = customerService.findAll();
        model.addAttribute("vouchers", vouchers);
        model.addAttribute("customers", customers);
        return "wallet/create-wallet";
    }
    @PostMapping("/wallets/create")
    public String createWallet(@RequestParam("email") String email, @RequestParam("voucherId")UUID voucherId){
        Voucher voucher = voucherService.findById(voucherId);
        WalletRequest request = new WalletRequest(email, voucher);
        service.createWallet(request);
        return "redirect:/wallets";
    }
    @GetMapping("/wallets/find-wallet")
    public String findByEmailForm(){
        return "wallet/wallet-find";
    }
    @GetMapping("/wallets/find-wallet/result")
    public String findByEmail(@RequestParam("email") String email, Model model){
        WalletRequest wallet = service.findByEmail(email);
        model.addAttribute("wallet", wallet);
        return "wallet/find-result";
    }

    @GetMapping("/wallets/find-voucher")
    public String findByVoucherForm(Model model){
        List<Voucher> allVoucher = voucherService.findAllVoucher();
        model.addAttribute("vouchers", allVoucher);
        return "wallet/find-by-voucher";
    }
    @GetMapping("/wallets/find-voucher/result")
    public String findByVoucher(@RequestParam("voucherId") UUID voucherId, Model model){
        Voucher voucher = voucherService.findById(voucherId);
        WalletRequest wallet = service.findByVoucher(voucher);
        model.addAttribute("wallet", wallet);
        return "wallet/find-voucher-result";
    }

    @GetMapping("wallets/delete")
    public String deleteForm(Model model){
        List<CustomerRequest> all = customerService.findAll();
        model.addAttribute("customers", all);
        return "wallet/delete";
    }
    @PostMapping("wallets/delete")
    public String deleteByEmail(@RequestParam("email") String email){
        try{
            service.deleteByEmail(email);
        }catch (NotExistEmailException e){
            return "지갑에선 존재하지 않는 고객의 이메일입니다";
        }
        return "redirect:/wallets";
    }
}
