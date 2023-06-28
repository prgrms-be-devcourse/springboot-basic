package com.wonu606.vouchermanager.service;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.VoucherVO;
import com.wonu606.vouchermanager.repository.VoucherRepository;
import com.wonu606.vouchermanager.service.factory.VoucherFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherFactory voucherFactory = new VoucherFactory();
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherVO voucherVO) {
        Voucher createdVoucher = voucherFactory.createVoucher(voucherVO);
        voucherRepository.save(createdVoucher);
        return createdVoucher;
    }

    public List<String> getVoucherTypes() {
        return new ArrayList<>(voucherFactory.getCreatableVoucherTypes());
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }
}
