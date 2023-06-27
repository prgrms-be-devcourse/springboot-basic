package com.wonu606.vouchermanager.service.factory;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import com.wonu606.vouchermanager.service.factory.creator.FixedAmountVoucherCreator;
import com.wonu606.vouchermanager.service.factory.creator.PercentageVoucherCreator;
import com.wonu606.vouchermanager.service.factory.creator.VoucherCreator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class VoucherFactory {

    private final Map<String, VoucherCreator> voucherCreatorMap = new HashMap<>();

    public VoucherFactory() {
        initVoucherCreatorMap();
    }

    public Voucher createVoucher(VoucherVO voucherVO) {
        UUID uuid = UUID.randomUUID();
        return createVoucherOfType(uuid, voucherVO);
    }

    public Set<String> getCreatableVoucherTypes() {
        return voucherCreatorMap.keySet();
    }

    private Voucher createVoucherOfType(UUID uuid, VoucherVO voucherVO) {
        String type = voucherVO.getType();
        VoucherCreator voucherCreator = voucherCreatorMap.get(type);
        if (voucherCreator == null) {
            throw new IllegalArgumentException("생성할 수 없는 타입입니다.");
        }
        return voucherCreator.create(uuid, voucherVO.getValue());
    }

    private void initVoucherCreatorMap() {
        voucherCreatorMap.put("fixed", new FixedAmountVoucherCreator());
        voucherCreatorMap.put("percentage", new PercentageVoucherCreator());
    }
}
