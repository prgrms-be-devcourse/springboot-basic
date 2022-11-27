package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.prgrms.kdtspringdemo.domain.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository, VoucherCreator voucherCreator) {
        this.voucherRepository = voucherRepository;
        this.voucherCreator = voucherCreator;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow(() ->
                new NullPointerException(MessageFormat.format("Can not find a voucher for{0}", voucherId)));
    }

    public Voucher createVoucher(VoucherType voucherType, Long number) throws IllegalArgumentException, IllegalStateException {
        Voucher newVoucher = voucherCreator.createVoucher(voucherType, number);
        var voucher = voucherRepository.insert(newVoucher);
        return voucher.orElse(null);
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public List<Voucher> getAllVoucherList() {
        return voucherRepository.findAllVaucher();
    }
}
