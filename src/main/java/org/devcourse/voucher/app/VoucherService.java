package org.devcourse.voucher.app;

import org.devcourse.voucher.app.dto.VoucherDto.VoucherListResponse;
import org.devcourse.voucher.app.dto.VoucherDto.VoucherSaveRequest;
import org.devcourse.voucher.app.dto.VoucherDto.VoucherSaveResponse;

public interface VoucherService {

    VoucherSaveResponse save(VoucherSaveRequest request);

    VoucherListResponse findAll();
}
