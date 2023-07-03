package kr.co.programmers.springbootbasic.wallet.dto;

import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;

import java.util.List;
import java.util.UUID;

public class CustomerWalletDto {
    private final String customerName;
    private final UUID walletId;
    private final List<VoucherDto> voucherDtos;

    public CustomerWalletDto(String customerName, UUID walletId, List<VoucherDto> voucherDtos) {
        this.customerName = customerName;
        this.walletId = walletId;
        this.voucherDtos = voucherDtos;
    }

    public String getCustomerName() {
        return customerName;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public List<VoucherDto> getVoucherDtos() {
        return voucherDtos;
    }
}
