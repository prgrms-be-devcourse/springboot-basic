package com.wonu606.vouchermanager.controller.voucher;

import com.wonu606.vouchermanager.controller.voucher.converter.VoucherCreateResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherGetOwnedCustomersResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.converter.VoucherGetResponseConverter;
import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherGetOwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherGetOwnedCustomersResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherGetResponse;
import com.wonu606.vouchermanager.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private final VoucherService service;


    private final VoucherCreateResponseConverter voucherCreateResponseConverter;
    private final VoucherGetResponseConverter voucherGetResponseConverter;
    private final VoucherGetOwnedCustomersResponseConverter voucherGetOwnedCustomersResponseConverter;

    public VoucherController(VoucherService service,
            VoucherCreateResponseConverter voucherCreateResponseConverter,
            VoucherGetResponseConverter voucherGetResponseConverter,
            VoucherGetOwnedCustomersResponseConverter voucherGetOwnedCustomersResponseConverter) {
        this.service = service;
        this.voucherCreateResponseConverter = voucherCreateResponseConverter;
        this.voucherGetResponseConverter = voucherGetResponseConverter;
        this.voucherGetOwnedCustomersResponseConverter = voucherGetOwnedCustomersResponseConverter;
    }

    public VoucherCreateResponse createVoucher(VoucherCreateRequest request) {
        VoucherCreateParam param = createVoucherCreateParam(request);
        VoucherCreateResult result = service.createVoucher(param);

        return voucherCreateResponseConverter.convert(result);
    }

    public VoucherGetResponse getVoucherList() {
        VoucherGetResult result = service.getVoucherList();

        return voucherGetResponseConverter.convert(result);
    }

    public VoucherGetOwnedCustomersResponse getOwnedCustomersByVoucher(
            VoucherGetOwnedCustomersRequest request) {
        VoucherGetOwnedCustomersParam param = createVoucherGetOwnedCustomersParam(request);
        VoucherGetOwnedCustomersResult result = service.findOwnedCustomersByVoucher(param);

        return voucherGetOwnedCustomersResponseConverter.convert(result);
    }

    public void assignWallet(WalletAssignRequest request) {
        WalletAssignParam param = createWalletAssignParam(request);
        WalletAssignResult result = service.assignWallet(param);
    }

    private VoucherCreateParam createVoucherCreateParam(VoucherCreateRequest request) {

    }

    private VoucherGetOwnedCustomersParam createVoucherGetOwnedCustomersParam(
            VoucherGetOwnedCustomersRequest request) {

    }
}
