package com.wonu606.vouchermanager.controller.voucher;

import com.wonu606.vouchermanager.controller.voucher.converter.OwnedCustomerResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.OwnedCustomersParamConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherCreateParamConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherCreateResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.WalletAssignParamConverter;
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
import com.wonu606.vouchermanager.service.voucherwallet.result.WalletAssignResult;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService service;


    private final VoucherCreateParamConverter voucherCreateParamConverter;
    private final VoucherCreateResponseConverter voucherCreateResponseConverter;
    private final VoucherResponseConverter voucherResponseConverter;
    private final OwnedCustomersParamConverter ownedCustomersParamConverter;
    private final OwnedCustomerResponseConverter ownedCustomerResponseConverter;
    private final WalletAssignParamConverter walletAssignParamConverter;

    public VoucherController(VoucherService service) {
        this.service = service;

        voucherCreateParamConverter = new VoucherCreateParamConverter();
        voucherCreateResponseConverter = new VoucherCreateResponseConverter();
        voucherResponseConverter = new VoucherResponseConverter();
        ownedCustomersParamConverter = new OwnedCustomersParamConverter();
        ownedCustomerResponseConverter = new OwnedCustomerResponseConverter();
        walletAssignParamConverter = new WalletAssignParamConverter();
    }

    public VoucherCreateResponse createVoucher(VoucherCreateRequest request) {
        VoucherCreateParam param = voucherCreateParamConverter.convert(request);
        VoucherCreateResult result = service.createVoucher(param);

        return voucherCreateResponseConverter.convert(result);
    }

    public List<VoucherResponse> getVoucherList() {
        List<VoucherResult> results = service.getVoucherList();

        return results.stream()
                .map(voucherResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public List<OwnedCustomerResponse> getOwnedCustomersByVoucher(OwnedCustomersRequest request) {
        OwnedCustomersParam param = ownedCustomersParamConverter.convert(request);
        List<OwnedCustomerResult> results = service.findOwnedCustomersByVoucher(param);

        return results.stream()
                .map(ownedCustomerResponseConverter::convert)
                .collect(Collectors.toList());
    }

    public void assignWallet(WalletAssignRequest request) {
        WalletAssignParam param = walletAssignParamConverter.convert(request);
        WalletAssignResult walletAssignResult = service.assignWallet(param);
    }
}
