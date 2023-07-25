package com.wonu606.vouchermanager.controller.voucherwallet;

import com.wonu606.vouchermanager.controller.voucherwallet.converter.VoucherWalletControllerConverterManager;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.controller.voucherwallet.response.OwnedCustomerResponse;
import com.wonu606.vouchermanager.controller.voucherwallet.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.service.voucherwallet.VoucherWalletService;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vouchers")
public class VoucherWalletController {

    private final VoucherWalletService service;
    private final VoucherWalletControllerConverterManager converterManager;

    public VoucherWalletController(VoucherWalletService service) {
        this.service = service;
        converterManager = new VoucherWalletControllerConverterManager();
    }

    @GetMapping("/create")
    public String createVoucherForm(Model model) {
        model.addAttribute("voucherCreateRequest", new VoucherCreateRequest("", 0.0));
        return "/vouchers/create-form";
    }

    @PostMapping("/create")
    public String createVoucher(@ModelAttribute VoucherCreateRequest request) {
        VoucherCreateParam param = converterManager.convert(request, VoucherCreateParam.class);
        service.createVoucher(param);

        return "redirect:/vouchers/list";
    }


    @GetMapping("/list")
    public String getVoucherList(Model model) {
        List<VoucherResult> results = service.getVoucherList();

        List<VoucherResponse> voucherList = results.stream()
                .map(rs -> converterManager.convert(rs, VoucherResponse.class))
                .collect(Collectors.toList());
        model.addAttribute("voucherList", voucherList);
        return "vouchers/list";
    }

    @GetMapping("/owned-customers")
    public String ownedCustomersList(Model model) {
        model.addAttribute("ownedCustomersRequest", new OwnedCustomersRequest());
        return "vouchers/owned-customers-form";
    }

    @PostMapping("/owned-customers")
    public String getOwnedCustomersByVoucher(@ModelAttribute OwnedCustomersRequest request,
            Model model) {
        OwnedCustomersParam param = converterManager.convert(request, OwnedCustomersParam.class);
        List<OwnedCustomerResult> results = service.findOwnedCustomersByVoucher(param);

        List<OwnedCustomerResponse> ownedCustomerResponses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedCustomerResponse.class))
                .collect(Collectors.toList());
        model.addAttribute("ownedCustomerResponses", ownedCustomerResponses);
        return "/vouchers/owned-customers-list";
    }

    @GetMapping("/assign-wallet")
    public String assignWallet(Model model) {
        model.addAttribute("walletAssignRequest", new WalletAssignRequest());
        return "/vouchers/assign-wallet-form";
    }

    @PostMapping("/assign-wallet")
    public String assignWallet(@ModelAttribute WalletAssignRequest walletAssignRequest) {
        WalletAssignParam param = converterManager.convert(walletAssignRequest,
                WalletAssignParam.class);
        service.assignWallet(param);
        return "redirect:/vouchers/list";
    }
}
