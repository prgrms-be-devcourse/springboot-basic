package devcourse.springbootbasic.service;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import devcourse.springbootbasic.repository.voucher.VoucherRepository;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public List<VoucherFindResponse> findAllVouchers() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }

    @Transactional
    public Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        return voucherRepository.save(voucherCreateRequest.toEntity(UUIDUtil.generateRandomUUID()));
    }

    @Transactional
    public Voucher updateDiscountValue(UUID voucherId, VoucherUpdateDiscountValueRequest voucherUpdateDiscountValueRequest) {
        Voucher updatedDiscountVoucher = this.getVoucherById(voucherId)
                .updateDiscountValue(voucherUpdateDiscountValueRequest.getDiscountValue());

        return persist(updatedDiscountVoucher);
    }

    @Transactional
    public Voucher deleteVoucher(UUID voucherId) {
        Voucher voucher = this.getVoucherById(voucherId);

        voucherRepository.delete(voucher);

        return voucher;
    }

    public Voucher assignVoucherToCustomer(Voucher voucher, Customer customer) {
        return persist(voucher.assignToCustomer(customer.getId()));
    }

    public List<VoucherFindResponse> findVouchersByCustomer(Customer customer) {
        return voucherRepository.findByCustomerId(customer.getId())
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }

    public Voucher unassignVoucherToCustomer(Voucher voucher) {
        return persist(voucher.unassignToCustomer());
    }

    public Voucher getVoucherById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> VoucherException.of(VoucherErrorMessage.NOT_FOUND));
    }

    private Voucher persist(Voucher voucher) {
        if (!voucherRepository.update(voucher)) {
            throw VoucherException.of(VoucherErrorMessage.NOT_FOUND);
        }

        return voucher;
    }
}
