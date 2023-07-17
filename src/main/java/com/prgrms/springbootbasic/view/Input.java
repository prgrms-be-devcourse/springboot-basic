package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.util.UUID;

public interface Input {

    //콘솔 명령어 입력
    String inputCommand();

    //바우처 생성(Create)
    VoucherCreateRequest inputVoucherCrateMessage(VoucherType type);

    //바우처 변경(Update)
    VoucherUpdateRequest inputVoucherUpdateMessage(UUID voucherId);

    //고객 생성(Create)
    CustomerCreateRequest inputCustomerCreateMessage();
    
    //고객 변경(Update)
    CustomerUpdateRequest inputCustomerUpdateMessage(UUID cusomterId);

    //ID값 입력
    UUID inputUUID();
}