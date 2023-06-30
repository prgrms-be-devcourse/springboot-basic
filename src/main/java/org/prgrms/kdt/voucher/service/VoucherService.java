package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.dto.VoucherDTO;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public VoucherService(@Qualifier("local") VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.
                findByiD(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public List<VoucherDTO> toVoucherDTOList() {
        return voucherRepository.findAll().
                stream().
                map(voucher -> new VoucherDTO(VoucherType.of(voucher.getVoucherTypeNum()),
                        voucher.getVoucherName(),
                        voucher.getBenefit()))
                .toList();
    }

    public Voucher createVoucher(VoucherType voucherType, long benefit) {
        Voucher voucher = voucherFactory.createVoucher(voucherType, UUID.randomUUID(), benefit);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public void userVoucher(Voucher voucher) {

    }

//    @Autowired
//    public void setVoucherRepository(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }
}
