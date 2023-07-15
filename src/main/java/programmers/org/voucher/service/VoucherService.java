package programmers.org.voucher.service;

import org.springframework.stereotype.Service;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.dto.VoucherRequest;
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

    public void create(VoucherRequest request) {
        Voucher voucher = toEntity(request);
        saveVoucher(voucher);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherRepository.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public VoucherResponse getVoucher(Long id) {
        Voucher voucher = findVoucherById(id);
        return toDto(voucher);
    }

    public void update(Long id, VoucherRequest request) {
        findVoucherById(id);
        voucherRepository.update(id, request);
    }

    public void delete(Long id) {
        findVoucherById(id);
        voucherRepository.deleteById(id);
    }

    private void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    private Voucher findVoucherById(Long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_VOUCHER.getMessage()));
    }

    private Voucher toEntity(VoucherRequest request) {
        return new Voucher(request.getDiscountAmount(), VoucherType.find(request.getType()));
    }

    private VoucherResponse toDto(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getDiscountAmount(), voucher.getType());
    }
}
