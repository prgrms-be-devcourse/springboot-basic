package com.wonu606.vouchermanager.service.factory.creator;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import java.util.UUID;

// interface
public interface VoucherCreator {

    public abstract Voucher create(UUID uuid, double value);
}
