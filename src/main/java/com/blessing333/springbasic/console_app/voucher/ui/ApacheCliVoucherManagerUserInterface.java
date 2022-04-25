package com.blessing333.springbasic.console_app.voucher.ui;

import com.blessing333.springbasic.console_app.ui.ApacheCommandLine;
import com.blessing333.springbasic.console_app.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.console_app.ui.CommandOptions;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("console")
@RequiredArgsConstructor
public class ApacheCliVoucherManagerUserInterface extends ApacheCommandLine implements VoucherManagerUserInterface {
    private static final String AVAILABLE_VOUCHER_TYPE_GUIDE = initAvailableVoucherTypeGuideText();
    private final Scanner scanner = new Scanner(System.in);

    private static String initAvailableVoucherTypeGuideText() {
        StringBuilder sb = new StringBuilder();
        Voucher.VoucherType.getValidVoucherTypes().forEach((optionNumber, voucherType) ->
                sb.append(optionNumber).append(". ").append(voucherType.getDescription()).append("\n")
        );
        return sb.toString();
    }

    @Override
    public void printVoucherInformation(Voucher voucher) {
        printDivider();
        printMessage(voucher.toString());
        printDivider();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String inputMessage() {
        return scanner.nextLine();
    }

    @Override
    public void showGuideText() {
        printMessage("=== Voucher Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new voucher.");
        printMessage("Type list to list all vouchers.");
        printMessage("=======================");
        printMessage("명령을 입력해주세요");
    }

    @Override
    public void showVoucherTypeSelectText() {
        printMessage("생성할 바우쳐의 타입을 입력하세요.");
        printMessage(AVAILABLE_VOUCHER_TYPE_GUIDE);
    }

    @Override
    public VoucherCreateFormPayload requestVoucherInformation() {
        showVoucherTypeSelectText();
        String voucherType = inputMessage();
        printMessage("할인 금액 혹은 비율을 입력하세요");
        String discountAmount = inputMessage();
        return new VoucherCreateFormPayload(voucherType, discountAmount);
    }

    @Override
    public void printVoucherCreateSuccessMessage(Voucher voucher) {
        printMessage("바우쳐 생성이 완료되었습니다.");
        printVoucherInformation(voucher);
    }

    @Override
    protected CommandOptions initSupportedCommandOption() {
        return CommandOptionConfigurer.configSupportedCommandOptions(VoucherCommandOptionType.getAvailableCommandOptionType());
    }

    @Override
    public void showHelpText() {
        printHelpText("바우처 관리");
    }
}
