package com.wonu606.vouchermanager.controller.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherControllerConverterManager;
import com.wonu606.vouchermanager.controller.voucher.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.controller.voucher.response.OwnedCustomerResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedCustomerResult;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService service;
    private final VoucherControllerConverterManager converterManager;

    public VoucherController(VoucherService service) {
        this.service = service;
        converterManager = new VoucherControllerConverterManager();
    }

    public String createVoucher(VoucherCreateRequest request) {
        VoucherCreateParam param = converterManager.convert(request, VoucherCreateParam.class);
        VoucherCreateResult result = service.createVoucher(param);

        VoucherCreateResponse response = converterManager.convert(result,
                VoucherCreateResponse.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getVoucherList() {
        List<VoucherResult> results = service.getVoucherList();

        List<VoucherResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, VoucherResponse.class))
                .collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(responses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOwnedCustomersByVoucher(OwnedCustomersRequest request) {
        OwnedCustomersParam param = converterManager.convert(request, OwnedCustomersParam.class);
        List<OwnedCustomerResult> results = service.findOwnedCustomersByVoucher(param);

        List<OwnedCustomerResponse> responses = results.stream()
                .map(rs -> converterManager.convert(rs, OwnedCustomerResponse.class))
                .collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(responses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignWallet(WalletAssignRequest request) {
        WalletAssignParam param = converterManager.convert(request, WalletAssignParam.class);
        service.assignWallet(param);
    }
}
