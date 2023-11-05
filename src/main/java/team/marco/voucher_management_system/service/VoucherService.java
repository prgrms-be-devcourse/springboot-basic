package team.marco.voucher_management_system.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.model.FixedAmountVoucher;
import team.marco.voucher_management_system.model.PercentDiscountVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.VoucherRepository;
import team.marco.voucher_management_system.type_enum.VoucherType;
import team.marco.voucher_management_system.web_app.dto.CreateVoucherRequest;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createFixedAmountVoucher(int amount) {
        Voucher voucher = new FixedAmountVoucher(amount);

        voucherRepository.save(voucher);

        return voucher;
    }

    public Voucher createPercentDiscountVoucher(int percent) {
        Voucher voucher = new PercentDiscountVoucher(percent);

        voucherRepository.save(voucher);

        return voucher;
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> findById(UUID id) {
        return voucherRepository.findById(id);
    }

    public List<Voucher> findByCreateAt(LocalDateTime from, LocalDateTime to) {
        return voucherRepository.findByCreateAt(from, to);
    }

    public Voucher create(CreateVoucherRequest createVoucherRequest) {
        return switch (createVoucherRequest.type()) {
            case FIXED -> createFixedAmountVoucher(createVoucherRequest.data());
            case PERCENT -> createPercentDiscountVoucher(createVoucherRequest.data());
        };
    }

    public boolean deleteById(UUID id) {
        int deleteCount = voucherRepository.deleteById(id);

        return deleteCount != 0;
    }

    public List<Voucher> findByType(VoucherType voucherType) {
        return voucherRepository.findByType(voucherType);
    }
}
