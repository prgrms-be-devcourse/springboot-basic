package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import devcourse.springbootbasic.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public Voucher create(VoucherCreateRequest voucherCreateRequest) {
        return voucherRepository.save(voucherCreateRequest.toEntity());
    }

    public List<VoucherFindResponse> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }

    @Transactional
    public Voucher updateDiscountValue(VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        Voucher updatedDiscountVoucher = this.findById(voucherUpdateDiscountValueRequest.getId())
                .updateDiscountValue(voucherUpdateDiscountValueRequest.getDiscountValue());

        return persist(updatedDiscountVoucher);
    }

    @Transactional
    public Voucher delete(UUID voucherId) {
        Voucher voucher = this.findById(voucherId);

        if (voucherRepository.delete(voucher) == 0) {
            throw VoucherException.of(VoucherErrorMessage.NOT_FOUND);
        }

        return voucher;
    }

    @Transactional
    public Voucher assignVoucherToCustomer(Voucher voucher, Customer customer) {
        return persist(voucher.assignToCustomer(customer.getId()));
    }

    private Voucher persist(Voucher voucher) {
        if (voucherRepository.update(voucher) == 0) {
            throw VoucherException.of(VoucherErrorMessage.NOT_FOUND);
        }

        return voucher;
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> VoucherException.of(VoucherErrorMessage.NOT_FOUND));
    }

    public List<VoucherFindResponse> findVouchersByCustomer(Customer customer) {
        return voucherRepository.findByCustomerId(customer.getId())
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }
}
