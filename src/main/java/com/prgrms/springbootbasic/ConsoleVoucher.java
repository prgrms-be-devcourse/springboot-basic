package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.controller.voucher.VoucherController;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.enums.VoucherDeleteMenu;
import com.prgrms.springbootbasic.enums.VoucherMenu;
import com.prgrms.springbootbasic.enums.VoucherSelectMenu;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.view.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsoleVoucher {

    private final VoucherController voucherController;
    private final Console console;

    public void menu() {
        console.printVoucherMenu();

        switch (VoucherMenu.of(console.inputCommand())) {
            case CREATE -> createVoucher();
            case UPDATE -> updateVoucher();
            case SELECT -> selectVoucher();
            case DELETE -> deleteVoucher();
        }
    }


    //생성(Create)
    private void createVoucher() {
        console.printVoucherCreateTypeMenu();
        VoucherType type = VoucherType.of(console.inputCommand());

        console.printVoucherCreateDiscountMenu();
        VoucherCreateRequest createRequest = console.inputVoucherCrateMessage(type);

        voucherController.create(createRequest);
        console.printCompleteMessage();
    }

    //변경(Update)
    private void updateVoucher() {
        console.printVoucherUpdateMenu();
        console.printVoucherUpdateId();
        console.inputUUID();
        UUID voucherId = console.inputUUID();
        VoucherUpdateRequest updateRequest = console.inputVoucherUpdateMessage(voucherId);

        voucherController.update(updateRequest);
        console.printCompleteMessage();
    }


    //읽기(Read) - 모든 바우처를 조회하려면 All,타입별로 조회를 하려면 Type, ID로 조회하려면 ID, 생성일별로 조회하려면 CreateAt을 입력
    private void selectVoucher() {
        console.printVoucherSelectMenu();
        //선택 - Id, Type, CreateAt, All
        switch (VoucherSelectMenu.of(console.inputCommand())) {
            case ID -> {
                console.printVoucherSelectId();
                UUID voucherId = console.inputUUID();

                if (!voucherController.checkVoucherId(voucherId)) {
                    console.printErrorMessage("해당 ID를 가진 Voucher를 찾을 수 없습니다.");
                }
            }
            //바우처 타입
            case TYPE -> {
                console.printVoucherSelectType();
                VoucherType voucherType = VoucherType.of(console.inputCommand());
                VoucherListResponse vouchersByType = voucherController.findByType(voucherType);
                if (!vouchersByType.getVoucherResponseList().isEmpty()) {
                    console.printVoucherSelectTypeList(vouchersByType);
                } else {
                    console.printErrorMessage("해당 타입의 바우처가 존재하지 않습니다.");
                }
            }
            //생성일 순으로 조회
            case CREATEAT -> {
                console.printVoucherSelectCreateAt();
                VoucherListResponse voucherListResponse = voucherController.findByCreateAt();
                if (!voucherListResponse.getVoucherResponseList().isEmpty()) {
                    console.printVoucherSelectAll(voucherListResponse);
                } else {
                    console.printErrorMessage("현재 저장된 바우처가 존재하지 않습니다.");
                }
            }
            //모든 리스트 조회
            case ALL -> {
                VoucherListResponse voucherListResponse = voucherController.findAllList();
                if (!voucherListResponse.getVoucherResponseList().isEmpty()) {
                    console.printVoucherSelectAll(voucherListResponse);
                } else {
                    console.printErrorMessage("현재 저장된 바우처가 없습니다.");
                }
                break;
            }
            default -> console.printErrorMessage("잘못된 voucherSelectMenu를 선택하셨습니다. 다시 확인해주세요.");
        }
    }

    //삭제(DELET)- id, all
    private void deleteVoucher() {
        console.printVoucherDeleteMenu();
        //선택 - id, all
        switch (VoucherDeleteMenu.of(console.inputCommand())) {
            case ID -> {
                console.printVoucherSelectId();
                UUID voucherId = console.inputUUID();

                if (!voucherController.checkVoucherId(voucherId)) {
                    console.printErrorMessage("해당 Voucher의 ID인" + voucherId + "찾을 수 없습니다.");
                    return;
                }
                voucherController.deleteById(voucherId);
            }
            case ALL -> {
                console.printVoucherDeleteAll();
                voucherController.deleteAll();
            }
            default -> {
                IllegalStateException e = new IllegalStateException("프로그램 삭제 명령어 오류");
                log.error("프로그램 삭제 명령어 오류", e);
                throw e;
            }
        }
    }
}
