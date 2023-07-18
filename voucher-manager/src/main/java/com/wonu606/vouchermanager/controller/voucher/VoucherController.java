package com.wonu606.vouchermanager.controller.voucher;

import com.wonu606.vouchermanager.controller.voucher.converter.VoucherCreateResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.OwnedCustomersResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherGetResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.controller.voucher.response.OwnedCustomersResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherResponse;
import com.wonu606.vouchermanager.service.voucher.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService service;


    private final VoucherCreateParamConverter voucherCreateParamConverter;
    private final OwnedCustomersParamConverter ownedCustomersParamConverter;

    private final VoucherCreateResponseConverter voucherCreateResponseConverter;
    private final VoucherGetResponseConverter voucherGetResponseConverter;
    private final OwnedCustomersResponseConverter ownedCustomersResponseConverter;

    public VoucherController(VoucherService service,
            VoucherCreateResponseConverter voucherCreateResponseConverter,
            VoucherGetResponseConverter voucherGetResponseConverter,
            OwnedCustomersResponseConverter OwnedCustomersResponseConverter) {
        this.service = service;
        this.voucherCreateResponseConverter = voucherCreateResponseConverter;
        this.voucherGetResponseConverter = voucherGetResponseConverter;
        this.ownedCustomersResponseConverter = OwnedCustomersResponseConverter;
    }

    public VoucherCreateResponse createVoucher(VoucherCreateRequest request) {
        VoucherCreateParam param = voucherCreateParamConverter.convert(request);
        VoucherCreateResult result = service.createVoucher(param);

        return voucherCreateResponseConverter.convert(result);
    }

    public VoucherResponse getVoucherList() {
        VoucherGetResult result = service.getVoucherList();

        return voucherGetResponseConverter.convert(result);
    }

    public OwnedCustomersResponse getOwnedCustomersByVoucher(OwnedCustomersRequest request) {
        OwnedCustomersParam param = ownedCustomersParamConverter.convert(request);
        OwnedCustomersResult result = service.findOwnedCustomersByVoucher(param);

        return ownedCustomersResponseConverter.convert(result);
    }

    public void assignWallet(WalletAssignRequest request) {
        WalletAssignParam param = createWalletAssignParam(request);
        WalletAssignResult result = service.assignWallet(param);
    }
}
