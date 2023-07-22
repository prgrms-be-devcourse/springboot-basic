package programmers.org.voucher.service;

import org.springframework.stereotype.Service;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.dto.VoucherResponse;
import programmers.org.voucher.repository.VoucherRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static programmers.org.voucher.exception.ErrorMessage.NOT_FOUND_VOUCHER;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void create(int discountAmount, VoucherType type) {
        Voucher voucher = new Voucher(discountAmount, type);
        saveVoucher(voucher);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public VoucherResponse getVoucher(Long id) {
        Voucher voucher = validateVoucherExist(id);
        return toDto(voucher);
    }

    public void update(Long id, int discountAmount) {
        validateVoucherExist(id);
        voucherRepository.update(id, discountAmount);
    }

    public void delete(Long id) {
        validateVoucherExist(id);
        voucherRepository.deleteById(id);
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    private Voucher validateVoucherExist(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER.getMessage()));
    }

    private VoucherResponse toDto(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getDiscountAmount(), voucher.getType());
    }
}
