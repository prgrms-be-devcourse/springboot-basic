package com.prgrms.vouchermanagement.core.voucher.controller.console;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreationRequest;
import com.prgrms.vouchermanagement.core.voucher.controller.response.VouchersResponse;
import com.prgrms.vouchermanagement.core.voucher.domain.VoucherType;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.prgrms.vouchermanagement.core.voucher.controller.Mapper.toVouchersResponse;

@Profile("dev")
@Controller
public class ConsoleVoucherController {

    private final VoucherService voucherService;

    @Autowired
    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 바우처 등록
     * @param voucherCreationRequest
     */
    public void createVoucher(VoucherCreationRequest voucherCreationRequest) {
        VoucherDto voucherDto = new VoucherDto(voucherCreationRequest.getName(), voucherCreationRequest.getAmount(), VoucherType.getType(voucherCreationRequest.getVoucherType()));
        voucherService.create(voucherDto);
    }

    /**
     * 모든 바우처 조회
     * @return VouchersResponse
     */
    public VouchersResponse getAllVoucher() {
        List<VoucherDto> voucherDtoList = voucherService.findAll();
        return toVouchersResponse(voucherDtoList);
    }
}
