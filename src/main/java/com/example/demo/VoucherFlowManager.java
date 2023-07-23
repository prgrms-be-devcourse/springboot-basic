package com.example.demo;

import com.example.demo.controller.VoucherController;
import com.example.demo.dto.VoucherDto;
import com.example.demo.enums.VoucherCommandType;
import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.voucher.VoucherView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class VoucherFlowManager {

    private final VoucherController voucherController;
    private final VoucherView voucherView;

    public void runMenu() {
        try {
            VoucherCommandType voucherCommandType = voucherView.readVoucherCommandOption();

            switch (voucherCommandType) {
                case CREATE_VOUCHER -> {
                    VoucherDiscountType voucherType = voucherView.readVoucherDiscountOption();
                    int amount = voucherView.readVoucherAmount(voucherType);
                    VoucherDto voucherDto = voucherController.create(voucherType, amount);
                    voucherView.printCreateMessage(voucherDto);
                }
                case PRINT_VOUCHER_LIST -> {
                    List<VoucherDto> voucherDtoList = voucherController.readList();
                    voucherView.printVoucherList(voucherDtoList);
                }
                case UPDATE_VOUCHER_AMOUNT -> {
                    UUID id = voucherView.readVoucherId();
                    //DB에서 바우처 타입 조회 후 amount에 대해 검증해야 함.
                    int discountAmount = voucherView.readVoucherAmountWithoutValidation();

                    voucherController.updateAmount(id, discountAmount);
                    voucherView.printUpdateMessage();
                }
                case DELETE_VOUCHER -> {
                    UUID id = voucherView.readVoucherId();
                    voucherController.delete(id);
                    voucherView.printDeleteMessage();
                }
                default -> throw new IllegalArgumentException(String.format("입력하신 %s은 올바르지 않은 커맨드입니다.", voucherCommandType.name()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

}
