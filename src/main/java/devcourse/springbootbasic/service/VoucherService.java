package devcourse.springbootbasic.service;

import devcourse.springbootbasic.commandline.exception.InputErrorMessage;
import devcourse.springbootbasic.commandline.exception.InputException;
import devcourse.springbootbasic.dto.VoucherCreateRequest;
import devcourse.springbootbasic.dto.VoucherFindResponse;
import devcourse.springbootbasic.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void create(VoucherCreateRequest voucherCreateRequest) {
        if (!voucherCreateRequest.validateDiscountValue()) {
            throw InputException.of(InputErrorMessage.INVALID_DISCOUNT_VALUE);
        }

        voucherRepository.save(voucherCreateRequest.toEntity());
    }

    public List<VoucherFindResponse> findAll() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherFindResponse::new)
                .toList();
    }
}
