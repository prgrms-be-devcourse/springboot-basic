package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.dto.VoucherDTO;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.prgrms.assignment.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private final VoucherFactory voucherFactory;

    public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository.
                findByID(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public List<VoucherDTO> convertToVoucherDTOs() {
        return voucherRepository.findAll().
                stream().
                map(voucher -> new VoucherDTO(voucher.getVoucherType(),
                    voucher.getBenefit(),
                    voucher.getCreatedAt()))
                .toList();
    }

    public void update(Voucher voucher) {
        voucherRepository.update(voucher);
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
