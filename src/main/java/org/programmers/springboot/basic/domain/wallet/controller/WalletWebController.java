package org.programmers.springboot.basic.domain.wallet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;
import org.programmers.springboot.basic.domain.customer.service.CustomerService;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.service.VoucherService;
import org.programmers.springboot.basic.domain.wallet.dto.WalletControllerRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.exception.DuplicateWalletException;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletMapper;
import org.programmers.springboot.basic.domain.wallet.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletWebController {

    private final WalletService walletService;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    @GetMapping("/addForm")
    public String addWallet(Model model) {
        model.addAttribute("vouchers", voucherService.findAll());
        return "wallet/addForm";
    }

    @GetMapping("/deleteForm")
    public String removeWallet() {
        return "wallet/deleteForm";
    }

    @GetMapping("/findVoucherForm")
    public String findAllVoucher() {
        return "wallet/findVoucherForm";
    }

    @GetMapping("/findCustomerForm")
    public String findAllCustomer() {
        return "wallet/findCustomerForm";
    }

    @PostMapping("/add")
    public String addVoucherInWallet(WalletControllerRequestDto requestDto, Model model) {

        String voucherId = null;
        String email = null;
        try {
            voucherId = requestDto.getVoucherId();
            email = requestDto.getEmail();

            WalletRequestDto walletRequestDto = WalletMapper.INSTANCE.mapToRequestDto(voucherId, email);
            walletService.createWallet(walletRequestDto);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
            model.addAttribute("err", e.getMessage());
        } catch (DuplicateWalletException e) {
            log.warn("email '{}' and voucherId '{}' is already exists in Wallet", email, voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of email '{}' found", email);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @DeleteMapping("/delete")
    public String removeWallet(WalletControllerRequestDto requestDto, Model model) {

        String voucherId = null;
        String email = null;
        try {
            voucherId = requestDto.getVoucherId();
            email = requestDto.getEmail();

            WalletRequestDto walletRequestDto = WalletMapper.INSTANCE.mapToRequestDto(voucherId, email);
            walletService.removeVoucherFromWallet(walletRequestDto);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: '{}'", voucherId);
            model.addAttribute("err", e.getMessage());
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
            model.addAttribute("err", e.getMessage());
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of email '{}' found", email);
            model.addAttribute("err", e.getMessage());
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @GetMapping("/vouchers")
    public String voucherListInWallet(@RequestParam String email, Model model) {

        try {
            WalletRequestDto walletRequestDto = WalletMapper.INSTANCE.mapToRequestDtoWithEmail(email);
            List<WalletResponseDto> wallets = walletService.walletListByEmail(walletRequestDto);
            CustomerResponseDto customerResponseDto = customerService.findByEmail(Email.valueOf(email));

            List<VoucherResponseDto> vouchers = new ArrayList<>();
            for (WalletResponseDto wallet: wallets) {
                vouchers.add(voucherService.findById(VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(wallet.getVoucherId().toString())));
            }

            model.addAttribute("customer", customerResponseDto);
            model.addAttribute("vouchers", vouchers);
            return "wallet/voucherListInWallet";
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }

    @GetMapping("/customers")
    public String customerListInWallet(@RequestParam String voucherId, Model model) {

        try {
            WalletRequestDto walletRequestDto = WalletMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
            List<WalletResponseDto> wallets = walletService.walletListByVoucherId(walletRequestDto);
            VoucherRequestDto requestDto = VoucherMapper.INSTANCE.mapToRequestDtoWithUUID(voucherId);
            VoucherResponseDto voucherResponseDto = voucherService.findById(requestDto);

            List<CustomerResponseDto> customers = new ArrayList<>();
            for (WalletResponseDto wallet : wallets) {
                customers.add(customerService.findByEmail(wallet.getEmail()));
            }

            model.addAttribute("voucher", voucherResponseDto);
            model.addAttribute("customers", customers);
            return "wallet/customerListInWallet";
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("err", e.getMessage());
        }
        return "error";
    }
}
