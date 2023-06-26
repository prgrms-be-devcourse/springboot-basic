package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.dto.VoucherDTO;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.
                findByiD(voucherId).
                orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Optional<List<VoucherDTO>> toVoucherDTOList() {
        if(voucherRepository.findAll().isEmpty())
            return Optional.empty();
        return Optional.of(voucherRepository.
                findAll().
                stream().
                map(voucher -> new VoucherDTO(VoucherType.of(voucher.getVoucherTypeNum()),
                        voucher.getVoucherName(),
                        voucher.getBenefit()))
                .toList());
    }

    public void userVoucher(Voucher voucher) {

    }

//    @Autowired
//    public void setVoucherRepository(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }
}
