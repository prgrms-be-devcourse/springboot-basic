package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.exception.Validator;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Console implements Input, Output {

    private static final Scanner input = new Scanner(System.in);

    //Input
    @Override
    public String inputCommand() {
        return input.nextLine();
    }

    //바우처 생성(Create)
    @Override
    public VoucherCreateRequest inputVoucherCrateMessage(VoucherType type) {
        String inputDiscount = input.nextLine();

        Validator.validateCreateVoucher(type, inputDiscount);
        return new VoucherCreateRequest(Long.parseLong(inputDiscount), type, LocalDateTime.now());
    }

    //바우처 변경(Update)
    @Override
    public VoucherUpdateRequest inputVoucherUpdateMessage(UUID voucherId) {
        String updateDiscount = input.nextLine();

        Validator.validateUpdateVoucher(updateDiscount);
        VoucherType voucherType = VoucherType.valueOf(input.nextLine());

        return new VoucherUpdateRequest(voucherId, Long.parseLong(updateDiscount), voucherType);
    }

    @Override
    public CustomerCreateRequest inputCustomerCreateMessage() {
        return null;
    }

    @Override
    public CustomerUpdateRequest inputCustomerUpdateMessage(UUID cusomterId) {
        return null;
    }

    @Override
    public UUID inputUUID() {

        try {
            return UUID.fromString(input.nextLine());
        } catch (IllegalArgumentException e) {
            log.error("입력된 값은 UUID 형식이 아닐 수 있습니다. 다시 한 번 확인해보세요. {}", e.getMessage());
            throw new IllegalArgumentException("입력된 값은 UUID 형식이 아닐 수 있습니다. 다시 한 번 확인해보세요.");
        }
    }


    //Output
    @Override
    public void printConsoleMenu() {
        System.out.println("==== Voucher Program ====");
        System.out.println(" 다음 아래의 3개의 프로그램 명령 중 하나를 선택해주세요.");
        System.out.println("Voucher : 바우처 관련 프로그램 시작");
        System.out.println("Customer : 고객 관련 프로그램 시작");
        System.out.println("Exit: 프로그램 종료");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherMenu() {
        System.out.println("==== Voucher Program ====");
        System.out.println("바우처 관련 프로그램을 시작합니다.");
        System.out.println("다음의 명령어 Create, Select, Update, Delete 중 하나를 입력해주세요.");
        System.out.println("바우처 생성: Create");
        System.out.println("바우처 조회: Select");
        System.out.println("바우처 변경: Update");
        System.out.println("바우처 삭제: Delete");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherCreateTypeMenu() {
        System.out.println("==== Voucher 생성 작업 ====");
        System.out.println("생성 가능한 바우처의 타입을 확인 후, 입력해 주세요!");
        System.out.println("정액 할인 바우처(고정 금액 할인)는 fixed를 입력해주세요.");
        System.out.println("정률 할인 바우처(비율 금액 할인)는 Rate를 입력해주세요.");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherCreateDiscountMenu() {
        System.out.println("==== Voucher 생성 작업 ==== ");
        System.out.println("생성할 바우처의 종류에 따른 입력 금액을 확인 후, 입력해 주세요!");
        System.out.println("정액 할인 바우처(고정 금액 할인)의 경우, 0이하의 숫자를 제외한 금액을 입력할 수 있습니다.");
        System.out.println("정률 할인 바우처(비율 금액 할인)의 경우, 1 ~ 99까지의 비율을 입력할 수 있습니다.");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherSelectMenu() {
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("바우처의 다음의 조회 방법에 대해서 확인 후, 입력해 주세요! ");
        System.out.println("All : 모든 바우처를 조회");
        System.out.println("ID : 바우처의 ID로 조회");
        System.out.println("Type: 바우처의 타입별로 조회");
        System.out.println("CreatedAt : 바우처의 생성일 순으로 조회 ");
        System.out.print("input: ");
    }

    @Override
    public void printVoucherSelectAll(VoucherListResponse voucherListResponse) {
        for (VoucherResponse voucherResponse : voucherListResponse.getVoucherResponseList()) {
            System.out.println("==== Voucher 조회 작업 ==== ");
            System.out.println("저장된 모든 바우처를 조회를 수행합니다.");
            System.out.println("바우처 ID : " + voucherResponse.getVoucherId());
            System.out.println("할인 금액 : " + voucherResponse.getDiscount());
            System.out.println("바우처 타입 : " + voucherResponse.getType());
            System.out.println("바우처 생성일 :" + voucherResponse.getCreateAt());
            System.out.println();
        }
    }

    @Override
    public void printVoucherSelectTypeList(VoucherListResponse voucherListResponse) {
        for (VoucherResponse voucherResponse : voucherListResponse.getVoucherResponseList()) {
            System.out.println("==== Voucher 조회 작업 ==== ");
            System.out.println(" 저장된 바우처를 타입별 조회를 수행합니다.");
            System.out.println();
            System.out.println("바우처 ID : " + voucherResponse.getVoucherId());
            System.out.println("할인 금액 : " + voucherResponse.getDiscount());
            System.out.println("바우처 타입 :  " + voucherResponse.getType());
            System.out.println("바우처 생성일 :" + voucherResponse.getCreateAt());
            System.out.println();
        }
    }

    @Override
    public void printVoucherSelectType() {
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("조회할 바우처의 Type을 입력해주세요!");
        System.out.println("fixed : 정액 할인 바우처(고정 금액 할인)");
        System.out.println("rate : 정률 할인 바우처(비율 금액 할인)");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherSelectId() {
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("조회할 바우처의 ID를 입력해주세요!");
        System.out.print("input : ");
    }


    @Override
    public void printVoucherSelectCreateAt() {
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("바우처를 생성일순으로 조회합니다.");
        System.out.println();
    }

    @Override
    public void printVoucherUpdateMenu() {
        System.out.println("==== Voucher 수정 작업 ==== ");
        System.out.println("바우처 수정을 선택하셨습니다.");
    }

    @Override
    public void printVoucherUpdateId() {
        System.out.println("수정할 바우처의 ID를 입력해주세요.");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherDeleteMenu() {
        System.out.println("==== Voucher 삭제 작업 ==== ");
        System.out.println("바우처 삭제를 작업을 선택하셨습니다.");
        System.out.println();
        System.out.println("ID: 바우처의  ID를 통해 해당 바우처 삭제");
        System.out.println("ALL : 모든 바우처들을 삭제 ");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherDeleteAll() {
        System.out.println("==== Voucher 삭제 작업 ==== ");
        System.out.println("모든 바우처들을 삭제를 선택하셨습니다!");
    }

    @Override
    public void printCustomerMenu() {
        System.out.println("고객 관련 프로그램 메뉴입니다.");
        System.out.println("아래의 명령어(Create,Read,Update,Delete) 중 하나를 입력해주세요");
        System.out.println("고객 생성: Create");
        System.out.println("고객 조회: Read");
        System.out.println("고객 변경: Update");
        System.out.println("고객 삭제: Delete");
    }

    @Override
    public void printExitMessage() {
        System.out.println("프로그램이 종료됩니다.");
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message.toString());
    }

    @Override
    public void printCompleteMessage() {
        System.out.println("정상적으로 해당 작업이 완료되었습니다!");
    }
}
