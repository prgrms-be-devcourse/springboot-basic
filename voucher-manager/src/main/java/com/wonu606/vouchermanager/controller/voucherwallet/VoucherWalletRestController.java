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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/vouchers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherWalletRestController {

    private final VoucherWalletService service;
    private final VoucherWalletControllerConverterManager converterManager;

    public VoucherWalletRestController(VoucherWalletService service) {
        this.service = service;
        converterManager = new VoucherWalletControllerConverterManager();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createVoucher(@RequestBody VoucherCreateRequest request) {
        VoucherCreateParam param = converterManager.convert(request, VoucherCreateParam.class);
        service.createVoucher(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<VoucherResponse>> getVoucherList() {
        List<VoucherResult> results = service.getVoucherList();

        List<VoucherResponse> voucherResponses = results.stream()
                .map(rs -> converterManager.convert(rs, VoucherResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(voucherResponses, HttpStatus.OK);
    }

    @PostMapping("/owned-customers")
    public ResponseEntity<List<OwnedCustomerResponse>> getOwnedCustomersByVoucher(@RequestBody OwnedCustomersRequest request) {
        OwnedCustomersParam param = converterManager.convert(request, OwnedCustomersParam.class);
        List<OwnedCustomerResult> results = service.findOwnedCustomersByVoucher(param);

        List<OwnedCustomerResponse> ownedCustomerResponses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedCustomerResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(ownedCustomerResponses, HttpStatus.OK);
    }

    @PostMapping("/assign-wallet")
    public ResponseEntity<Void> assignWallet(@RequestBody WalletAssignRequest walletAssignRequest) {
        WalletAssignParam param = converterManager.convert(walletAssignRequest, WalletAssignParam.class);
        service.assignWallet(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
