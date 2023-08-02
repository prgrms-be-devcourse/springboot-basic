package com.prgrms.voucher.service.dto;

import com.prgrms.voucher.model.VoucherType;
import java.time.LocalDateTime;

public record VoucherServiceListRequest(VoucherType voucherType,
                                        LocalDateTime startCreatedAt) { }
