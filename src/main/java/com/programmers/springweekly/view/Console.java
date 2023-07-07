package com.programmers.springweekly.view;

import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.customer.response.CustomerListResponse;
import com.programmers.springweekly.dto.customer.response.CustomerResponse;
import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.util.Validator;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class Console implements Input, Output {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String NEW_LINE = "=========================================================";

    @Override
    public String inputMessage() {
        return SCANNER.nextLine();
    }

    @Override
    public VoucherCreateRequest inputVoucherCreate(VoucherType voucherType) {
        String inputDiscountAmount = SCANNER.nextLine();
        Validator.validateDiscountAmount(voucherType, inputDiscountAmount);

        return VoucherCreateRequest.builder()
                .discountAmount(Long.parseLong(inputDiscountAmount))
                .voucherType(voucherType)
                .build();
    }

    @Override
    public VoucherUpdateRequest inputVoucherUpdate(UUID voucherId) {
        String[] voucherInfo = Validator.inputParse(SCANNER.nextLine());
        VoucherType voucherType = VoucherType.from(voucherInfo[1]);
        Validator.validateDiscountAmount(voucherType, voucherInfo[0]);

        return VoucherUpdateRequest.builder()
                .voucherId(voucherId)
                .discountAmount(Long.parseLong(voucherInfo[0]))
                .voucherType(voucherType)
                .build();
    }


    @Override
    public CustomerCreateRequest inputCustomerCreate() {
        String[] customerInfo = Validator.inputParse(SCANNER.nextLine());
        Validator.validateName(customerInfo[0]);
        Validator.validateEmail(customerInfo[1]);

        return CustomerCreateRequest.builder()
                .customerName(customerInfo[0])
                .customerEmail(customerInfo[1])
                .customerType(CustomerType.from(customerInfo[2]))
                .build();
    }

    @Override
    public CustomerUpdateRequest inputCustomerUpdate(UUID customerId) {
        String[] customerInfo = Validator.inputParse(SCANNER.nextLine());
        Validator.validateName(customerInfo[0]);
        Validator.validateEmail(customerInfo[1]);

        return CustomerUpdateRequest.builder()
                .customerId(customerId)
                .customerName(customerInfo[0])
                .customerEmail(customerInfo[1])
                .customerType(CustomerType.from(customerInfo[2]))
                .build();
    }

    @Override
    public UUID inputUUID() {
        return UUID.fromString(SCANNER.nextLine());
    }

    @Override
    public void outputProgramGuide() {
        System.out.println("====== 바우처 프로그램 ======");
        System.out.println("customer : 고객 관련된 작업");
        System.out.println("voucher : 바우처 관련된 작업");
        System.out.println("exit : 프로그램 종료");
    }

    @Override
    public void outputSelectCreateVoucherGuide() {
        System.out.println("바우처 종류 : 고정 할인은 fixed, 퍼센트 할인은 percent를 입력해주세요");
    }

    @Override
    public void outputDiscountGuide() {
        System.out.println("할인 양 : 고정 할인은 자유롭게, 퍼센트는 1부터 100까지 사이의 숫자만 입력해주세요.");
    }

    @Override
    public void outputExitMessage() {
        System.out.println("프로그램이 종료되었습니다.");
    }

    @Override
    public void outputGetVoucherAll(VoucherListResponse voucherListResponse) {
        for (VoucherResponse voucher : voucherListResponse.getVoucherList()) {
            System.out.println(NEW_LINE);
            System.out.println("바우처 ID : " + voucher.getVoucherId());
            System.out.println("할인 양 : " + voucher.getDiscountAmount());
            System.out.println("바우처 타입 : " + voucher.getVoucherType());
            System.out.println(NEW_LINE + "\n");
        }
    }

    @Override
    public void outputVoucherUpdateGuide() {
        System.out.println("바우처를 업데이트 하기 위해서 아래 순서대로 입력하시되 ,(쉼표)를 기준으로 입력해주세요.");
        System.out.println("할인 양 : 고정 할인은 자유롭게, 퍼센트는 1부터 100까지 사이의 숫자만 입력해주세요.");
        System.out.println("바우처 종류 : 고정 할인은 fixed, 퍼센트 할인은 percent를 입력해주세요");
        System.out.println("예시) 바우처 할인 양,바우처 종류");
    }

    @Override
    public void outputGetCustomerList(CustomerListResponse customerList) {
        for (CustomerResponse customer : customerList.getCustomerList()) {
            System.out.println(NEW_LINE);
            System.out.println("고객 ID : " + customer.getCustomerId());
            System.out.println("고객 이름 : " + customer.getCustomerName());
            System.out.println("고객 이메일 : " + customer.getCustomerEmail());
            System.out.println("고객 타입 : " + customer.getCustomerType());
            System.out.println(NEW_LINE + "\n");
        }
    }

    @Override
    public void outputErrorMessage(String errorText) {
        System.out.println(errorText);
    }

    @Override
    public void outputUUIDGuide() {
        System.out.println("고객 UUID를 입력해주세요.");
    }

    @Override
    public void outputCustomerUpdateGuide() {
        System.out.println("고객을 업데이트 하기 위해서 아래 순서대로 입력하시되 ,(쉼표)를 기준으로 영어로 입력해주세요.");
        System.out.println("예시) 고객 이름,고객 이메일,고객 타입(normal, blacklist)");
    }

    @Override
    public void outputCompleteGuide() {
        System.out.println("작업이 처리되었습니다.");
    }

    @Override
    public void outputCustomerCreateGuide() {
        System.out.println("고객을 생성 하기 위해서 아래 순서대로 입력하시되 ,(쉼표)를 기준으로 영어로 입력해주세요.");
        System.out.println("예시) 고객 이름,고객 이메일,고객 타입(normal, blacklist)");
    }

    @Override
    public void outputCustomerMenuGuide() {
        System.out.println("=========== 고객 프로그램 ===========");
        System.out.println("create : 고객을 생성합니다.");
        System.out.println("update : 고객을 업데이트합니다.");
        System.out.println("delete : 고객을 삭제합니다.");
        System.out.println("select : 고객을 모두 조회합니다.");
        System.out.println("blacklist : 블랙리스트 고객을 모두 조회합니다.");
    }

    @Override
    public void outputVoucherMenuGuide() {
        System.out.println("=========== 바우처 프로그램 ===========");
        System.out.println("create : 바우처를 생성합니다.");
        System.out.println("update : 바우처를 업데이트합니다.");
        System.out.println("delete : 바우처를 삭제합니다.");
        System.out.println("select : 바우처를 모두 조회합니다.");
    }
}
