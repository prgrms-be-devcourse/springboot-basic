package org.promgrammers.springbootbasic.domain.voucher.dto.request;

import java.util.UUID;

public record UpdateVoucherRequest(UUID voucherId, long discountAmount) {

}
