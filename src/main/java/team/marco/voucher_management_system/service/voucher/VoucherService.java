package team.marco.voucher_management_system.service.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;
import team.marco.voucher_management_system.repository.voucher.VoucherRepository;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher createVoucher(VoucherCreateServiceRequest request) {
        Long latestId = voucherRepository.findLatestVoucherId().orElse(0L);

        Voucher.Builder builder = new Voucher.Builder(latestId + 1, request.getVoucherType(), request.getDiscountValue());
        request.getCode().ifPresent(code -> builder.code(code));
        request.getName().ifPresent(name -> builder.name(name));
        Voucher voucher = builder.build();

        return voucherRepository.save(voucher);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }
    public List<Voucher> getVouchersByVoucherType(VoucherType type) {
        return voucherRepository.findAllByVoucherType(type);
    }

    public Voucher getVoucher(Long voucherId) {
        return voucherRepository.findById(voucherId).orElseThrow();
    }

    @Transactional
    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }
}
