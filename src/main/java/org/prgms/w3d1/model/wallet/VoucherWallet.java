package org.prgms.w3d1.model.wallet;

import org.prgms.w3d1.model.voucher.Voucher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VoucherWallet implements Wallet{

    private final UUID voucherWalletId;
    private final UUID customerId;
    private List<Voucher> voucherList;

    public VoucherWallet(UUID voucherWalletId, UUID customerId) {
        this.voucherWalletId = voucherWalletId;
        this.customerId = customerId;
    }

    public VoucherWallet(UUID voucherWalletId, UUID customerId, List<Voucher> voucherList) {
        this.voucherWalletId = voucherWalletId;
        this.voucherList = voucherList;
        this.customerId = customerId;
    }

    public UUID getVoucherWalletId() {
        return voucherWalletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoucherWallet that = (VoucherWallet) o;

        if (voucherWalletId != null ? !voucherWalletId.equals(that.voucherWalletId) : that.voucherWalletId != null)
            return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        return voucherList != null ? voucherList.equals(that.voucherList) : that.voucherList == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherWalletId, customerId, voucherList);
    }
}
