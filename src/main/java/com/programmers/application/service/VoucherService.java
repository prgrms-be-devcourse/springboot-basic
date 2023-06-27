package com.programmers.application.service;

import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.VoucherCreationRequest;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    UUID createVoucher(VoucherCreationRequest voucherCreationRequest);

    List<VoucherInfoResponse> findVoucherList();
}
