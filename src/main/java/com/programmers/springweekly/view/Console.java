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
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.util.validator.CustomerValidator;
import com.programmers.springweekly.util.validator.ParseValidator;
import com.programmers.springweekly.util.validator.VoucherValidator;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        log.info("사용자가 입력한 할인 양 : {}", inputDiscountAmount);

        VoucherValidator.validateVoucher(voucherType, inputDiscountAmount);

        return VoucherCreateRequest.builder()
                .discountAmount(Long.parseLong(inputDiscountAmount))
                .voucherType(voucherType)
                .build();
    }

    @Override
    public VoucherUpdateRequest inputVoucherUpdate(UUID voucherId) {
        String[] voucherInfo = ParseValidator.inputParse(SCANNER.nextLine());
        log.info("사용자가 입력한 업데이트 바우처 정보 : {}", Arrays.toString(voucherInfo));

        ParseValidator.validateVoucherUpdateLength(voucherInfo);

        VoucherType voucherType = VoucherType.from(voucherInfo[1]);
        VoucherValidator.validateVoucher(voucherType, voucherInfo[0]);

        return VoucherUpdateRequest.builder()
                .voucherId(voucherId)
                .discountAmount(Long.parseLong(voucherInfo[0]))
                .voucherType(voucherType)
                .build();
    }

    @Override
    public CustomerCreateRequest inputCustomerCreate() {
        String[] customerInfo = ParseValidator.inputParse(SCANNER.nextLine());
        log.info("사용자가 입력한 고객 정보 : {}", Arrays.toString(customerInfo));

        ParseValidator.validateCustomerLength(customerInfo);

        CustomerValidator.validateInputCustomerInfo(customerInfo[0], customerInfo[1]);

        return CustomerCreateRequest.builder()
                .customerName(customerInfo[0])
                .customerEmail(customerInfo[1])
                .customerType(CustomerType.from(customerInfo[2]))
                .build();
    }

    @Override
    public CustomerUpdateRequest inputCustomerUpdate(UUID customerId) {
        String[] customerInfo = ParseValidator.inputParse(SCANNER.nextLine());

        ParseValidator.validateCustomerLength(customerInfo);

        CustomerValidator.validateInputCustomerInfo(customerInfo[0], customerInfo[1]);

        return CustomerUpdateRequest.builder()
                .customerId(customerId)
                .customerName(customerInfo[0])
                .customerEmail(customerInfo[1])
                .customerType(CustomerType.from(customerInfo[2]))
                .build();
    }

    @Override
    public UUID inputUUID() {
        try {
            return UUID.fromString(SCANNER.nextLine());
        } catch (IllegalArgumentException e) {
            log.error("입력된 값은 UUID 형식이 아닐 수 있습니다. 다시 한 번 확인해보세요. {}", e.getMessage());
            throw new IllegalArgumentException("입력된 값은 UUID 형식이 아닐 수 있습니다. 다시 한 번 확인해보세요.");
        }
    }

    @Override
    public void outputProgramGuide() {
        System.out.println("====== 바우처 프로그램 ======");
        System.out.println("customer : 고객 관련된 작업");
        System.out.println("voucher : 바우처 관련된 작업");
        System.out.println("wallet : 바우처 지갑과 관련된 작업");
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
    public void outputGetWalletList(WalletsResponse walletList) {
        for (WalletResponse wallet : walletList.getWalletList()) {
            System.out.println(NEW_LINE);
            System.out.println("바우처 지갑 ID : " + wallet.getWalletId());
            System.out.println("고객 ID  : " + wallet.getCustomerId());
            System.out.println("할당된 바우처 ID : " + wallet.getVoucherId());
            System.out.println(NEW_LINE + "\n");
        }
    }

    @Override
    public void outputGetWalletListByVoucher(WalletsResponse walletList) {
        System.out.println(NEW_LINE);
        System.out.println("바우처 ID : " + walletList.getWalletList().get(0).getVoucherId());
        System.out.println(NEW_LINE);
        for (WalletResponse wallet : walletList.getWalletList()) {
            System.out.println("할당된 고객 ID  : " + wallet.getCustomerId());

        }
        System.out.println(NEW_LINE + "\n");
    }

    @Override
    public void outputGetWallet(WalletResponse wallet) {
        System.out.println(NEW_LINE);
        System.out.println("고객 ID : " + wallet.getCustomerId());
        System.out.println("할당된 바우처 ID : " + wallet.getVoucherId());
        System.out.println(NEW_LINE + "\n");
    }

    @Override
    public void outputErrorMessage(String errorText) {
        System.out.println(errorText);
    }

    @Override
    public void outputCustomerUUIDGuide() {
        System.out.println("고객 UUID를 입력해주세요.");
    }

    @Override
    public void outputVoucherUUIDGuide() {
        System.out.println("바우처v UUID를 입력해주세요.");
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
    public void outputCompleteGuideContainMsg(String message) {
        System.out.println(message);
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

    @Override
    public void outputWalletMenuGuide() {
        System.out.println("=========== 바우처 지갑 프로그램 ===========");
        System.out.println("assign : 바우처를 고객에게 할당합니다.");
        System.out.println("delete : 고객에게 할당된 바우처를 삭제합니다.");
        System.out.println("findAll : 모든 고객에게 할당된 바우처 정보를 조회합니다.");
        System.out.println("findBycustomer : 고객에게 할당된 바우처를 조회합니다.");
        System.out.println("findByVoucher : 해당 바우처가 할당된 모든 고객을 조회합니다.");
    }

    @Override
    public void outputWalletCustomerUUIDGuide() {
        System.out.println("바우처를 할당하려는 고객의 ID를 입력해주세요");
    }

    @Override
    public void outputWalletVoucherUUIDGuide() {
        System.out.println("고객에게 할당하려는 바우처의 ID를 입력해주세요");
    }

    @Override
    public void outputWalletUUIDGuide() {
        System.out.println("삭제하려는 바우처 지갑의 ID를 입력해주세요");
    }

    @Override
    public void outputWalletCustomerUUIDToFind() {
        System.out.println("고객에게 할당된 바우처를 찾기 위해 고객의 ID를 입력해주세요");
    }

    @Override
    public void outputWalletVoucherUUIDToFind() {
        System.out.println("해당 바우처를 가진 고객들을 찾기 위해 바우처의 ID를 입력해주세요");
    }

}
