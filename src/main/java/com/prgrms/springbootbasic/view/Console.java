package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.customer.response.CustomerResponse;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
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
        return new VoucherCreateRequest(Long.parseLong(inputDiscount), type, LocalDateTime.now());
    }

    //바우처 변경(Update)
    @Override
    public VoucherUpdateRequest inputVoucherUpdateMessage(UUID voucherId) {
        System.out.print("변경할 금액 : ");
        String updateDiscount = input.nextLine();

        return new VoucherUpdateRequest(voucherId, Long.parseLong(updateDiscount));
    }

    @Override
    public UUID inputUUID() {
        try {
            return UUID.fromString(input.nextLine());
        } catch (IllegalArgumentException e) {
            log.error("입력된 값은 UUID 형식이 아닙니다.", e.getMessage());
            throw new IllegalArgumentException("입력된 값은 UUID 형식이 아닙니다. 다시 한 번 확인해보세요.");
        }
    }

    //Output
    @Override
    public void printConsoleMenu() {
        System.out.println();
        System.out.println("==== Voucher Program ====");
        System.out.println(" 다음 아래의 3개의 프로그램 명령 중 하나를 선택해주세요.");
        System.out.println("Voucher : 바우처 관련 프로그램 시작");
        System.out.println("Customer : 고객 관련 프로그램 시작");
        System.out.println("Exit: 프로그램 종료");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherMenu() {
        System.out.println();
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
        System.out.println();
        System.out.println("==== Voucher 생성 작업 ==== ");
        System.out.println("생성할 바우처의 종류에 따른 입력 금액을 확인 후, 입력해 주세요!");
        System.out.println("정액 할인 바우처(고정 금액 할인)의 경우, 0이하의 숫자를 제외한 금액을 입력할 수 있습니다.");
        System.out.println("정률 할인 바우처(비율 금액 할인)의 경우, 1 ~ 99까지의 비율을 입력할 수 있습니다.");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherSelectMenu() {
        System.out.println();
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("바우처의 다음의 조회 방법에 대해서 확인 후, 입력해 주세요! ");
        System.out.println("All : 모든 바우처를 조회");
        System.out.println("ID : 바우처의 ID로 조회");
        System.out.println("Type: 바우처의 타입별로 조회");
        System.out.println("CreatedAt : 바우처의 생성일 순으로 조회 ");
        System.out.print("input: ");
    }

    @Override
    public void printVoucherSelectBYAll(VoucherListResponse voucherListResponse) {
        for (VoucherResponse voucherResponse : voucherListResponse.getVoucherResponseList()) {
            System.out.println();
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
    public void printVoucherSelectByTypeList(VoucherListResponse voucherListResponse) {
        for (VoucherResponse voucherResponse : voucherListResponse.getVoucherResponseList()) {
            System.out.println();
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
    public void printVoucherSelectByType() {
        System.out.println();
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("조회할 바우처의 Type을 입력해주세요!");
        System.out.println("fixed : 정액 할인 바우처(고정 금액 할인)");
        System.out.println("rate : 정률 할인 바우처(비율 금액 할인)");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherSelectById() {
        System.out.println();
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("조회할 바우처의 ID를 입력해주세요!");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherSelectByCreateAt(VoucherListResponse voucherListResponse) {
        System.out.println();
        System.out.println("==== Voucher 조회 작업 ==== ");
        System.out.println("바우처를 생성일순으로 조회합니다.");
        System.out.println();
    }

    @Override
    public void printVoucherUpdateMenu() {
        System.out.println();
        System.out.println("==== Voucher 수정 작업 ==== ");
        System.out.println("바우처 수정을 선택하셨습니다.");
    }

    @Override
    public void printVoucherUpdateById() {
        System.out.println();
        System.out.println("수정할 바우처의 ID를 입력해주세요.");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherDeleteMenu() {
        System.out.println();
        System.out.println("==== Voucher 삭제 작업 ==== ");
        System.out.println("바우처 삭제를 작업을 선택하셨습니다.");
        System.out.println();
        System.out.println("ID: 바우처의  ID를 통해 해당 바우처 삭제");
        System.out.println("ALL : 모든 바우처들을 삭제 ");
        System.out.print("input : ");
    }

    @Override
    public void printVoucherDeleteById() {
        System.out.println("삭제할 바우처의 ID를 입력해주세요.");
        System.out.print("input: ");
    }

    @Override
    public void printVoucherDeleteAll() {
        System.out.println("모든 바우처들을 삭제를 선택하셨습니다!");
    }


    //고객 관련 메뉴들
    @Override
    public void printCustomerMenu() {
        System.out.println();
        System.out.println("==== Customer 프로그램 작업 ==== ");
        ;
        System.out.println("아래의 명령어(Create,Select,Update,Delete) 중 하나를 입력해주세요");
        System.out.println("고객 생성: Create");
        System.out.println("고객 조회: Select");
        System.out.println("고객 변경: Update");
        System.out.println("고객 삭제: Delete");
        System.out.print("input : ");
    }


    @Override
    public void printCustomerCreateMenu() {
        System.out.println();
        System.out.println("==== Customer 생성 작업 ====");
        System.out.println("반드시 다음과 같은 순서로 입력해주세요!");
        System.out.println("1.고객 이름(Name)");
        System.out.println("2.고객 이메일(Email)");
    }

    @Override
    public void printCustomerSelectMenu() {
        System.out.println();
        System.out.println("==== Customer 조회 작업 ==== ");
        System.out.println("고객 조회에 대해서  다음의 방법을 확인 후, 입력해 주세요! ");
        System.out.println("ID : 고객의 ID로 조회");
        System.out.println("CreatedAt : 고객의 생성일 순으로 조회 ");
        System.out.println("ALL: 모든 고객을 조회");
        System.out.print("input: ");
    }

    @Override
    public void printCustomerSelectAll(CustomerListResponse customerListResponse) {
        for (CustomerResponse customerResponse : customerListResponse.getCustomerResponseList()) {
            System.out.println();
            System.out.println("==== Customer 조회 작업 ==== ");
            System.out.println("저장된 모든 고객을 조회를 수행합니다.");
            System.out.println("고객 ID : " + customerResponse.getCustomerId());
            System.out.println("고객 Name : " + customerResponse.getCustomerName());
            System.out.println("고객 Email :  " + customerResponse.getCustomerEmail());
            System.out.println("고객 생성일 :" + customerResponse.getCreateAt());
            System.out.println();
        }
    }


    @Override
    public void printCustomerSelectById() {
        System.out.println();
        System.out.println("==== Customer 조회 작업 ==== ");
        System.out.println("조회할 고객의 ID를 입력해주세요!");
        System.out.print("input : ");
    }

    @Override
    public void printCutomerSelectByCreatedAt() {
        System.out.println();
        System.out.println("==== Customer 조회 작업 ==== ");
        System.out.println("고객을 생성일순으로 조회합니다.");
        System.out.println();
    }


    //고객 수정(Update) - 메뉴
    public CustomerUpdateRequest inputCustomerUpdateMessage(UUID customerId) {
        System.out.print("변경할 이름 : ");
        String updateCustomerName = input.nextLine();

        System.out.print("변경할 이메일 : ");
        String updateCustomerEmail = input.nextLine();

        // 업데이트 요청 객체 반환
        return new CustomerUpdateRequest(customerId, updateCustomerName, updateCustomerEmail);
    }

    @Override
    public void printCustomerUpdateByID() {
        System.out.println();
        System.out.println("수정햘 고객의 ID를 입력해주세요.");
        System.out.print("input : ");
    }

    //고객 - 삭제 메뉴
    @Override
    public void printCustomerDeleteMenu() {
        System.out.println();
        System.out.println("==== Customer 삭제 작업 ==== ");
        System.out.println("고객 삭제 작업을 선택하셨습니다.");
        System.out.println();
        System.out.println("ID: 고객의  ID를 통해 해당 바우처 삭제");
        System.out.println("ALL : 모든 고객을 삭제 ");
        System.out.print("input : ");
    }

    //고객 삭제 -ID
    @Override
    public void printCustomerDeleteByID() {
        System.out.println();
        System.out.println("삭제할 고객의 ID를 입력해주세요.");
        System.out.print("input : ");
    }

    //고객 삭제 - All
    @Override
    public void printCustomerDeleteByAll() {
        System.out.println();
        System.out.println("==== Customer 삭제 작업 ==== ");
        System.out.println("모든 고객 삭제를 선택하셨습니다!");
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

    public void printCustomerSelectByCreatedAt(CustomerListResponse customerListResponse) {
        for (CustomerResponse customerResponse : customerListResponse.getCustomerResponseList()) {
            System.out.println();
            System.out.println("==== 고객 조회 작업(생성일 순) ==== ");
            System.out.println("저장된 모든 고객를 조회를 수행합니다.");
            System.out.println("고객 ID : " + customerResponse.getCustomerId());
            System.out.println("고객 이름 : " + customerResponse.getCustomerName());
            System.out.println("고객 이메일 : " + customerResponse.getCustomerEmail());
            System.out.println("고객 생성일 :" + customerResponse.getCreateAt());
            System.out.println();
        }
    }

    @Override
    public CustomerCreateRequest inputCustomerCreateMessage() {
        System.out.print("input(이름) : ");
        String inputCustomerName = input.nextLine();

        System.out.print("input(Email) :  ");
        String inputCustomerEmail = input.nextLine();
        return new CustomerCreateRequest(inputCustomerName, inputCustomerEmail, LocalDateTime.now());
    }
}
