package org.prgrms.springbootbasic.service;

import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.factory.VoucherFactory;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.prgrms.springbootbasic.type.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final List<VoucherFactory> voucherFactoryList;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, List<VoucherFactory> voucherFactoryList) {
        this.voucherRepository = voucherRepository;
        this.voucherFactoryList = voucherFactoryList;
    }

    public Voucher createVoucher(VoucherType voucherType, long quantity) {
        Voucher voucher = null;
        for (VoucherFactory voucherFactory : voucherFactoryList) {
            if (isTypeEquals(voucherType, voucherFactory)) {
                voucher = voucherFactory.createVoucher(quantity);
                voucherRepository.insert(voucher);
                break;
            }
        }
        return voucher;
    }

    public List<Voucher> lookupVoucherList() {
        return voucherRepository.findAll();
    }

    private boolean isTypeEquals(VoucherType voucherType, VoucherFactory factory) {
        return Objects.equals(factory.getClass(), voucherType.getVoucherFactoryClass());
    }
}
