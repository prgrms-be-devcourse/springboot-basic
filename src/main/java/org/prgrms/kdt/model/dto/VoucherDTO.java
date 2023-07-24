package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public record VoucherDTO(Long voucherId, Amount amount, VoucherType voucherType) {

}
