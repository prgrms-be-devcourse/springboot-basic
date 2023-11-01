package com.programmers.springbootbasic.infrastructure.IO;

import com.programmers.springbootbasic.domain.user.presentation.dto.CreateUserRequest;
import com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto.CreateUserVoucherWalletRequest;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.CreateVoucherRequest;
import com.programmers.springbootbasic.mediator.dto.UpdateVoucherMediatorRequest;
import com.programmers.springbootbasic.util.Messages;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInteractionAggregator {

    private final Console console;

    public ConsoleInteractionAggregator(Console console) {
        this.console = console;
    }

    public String collectMenuInput() {
        return console.collectStringInput(Messages.SELECT_MENU.getMessage());
    }

    public CreateVoucherRequest collectVoucherInput() {
        String type = console.collectStringInput(Messages.VOUCHER_REGISTER_TYPE.getMessage());
        int amount = console.collectIntegerInput(Messages.VOUCHER_REGISTER_AMOUNT.getMessage());
        return CreateVoucherRequest.of(VoucherTypeEnum.of(type), amount);
    }

    public CreateUserRequest collectUserInput() {
        String nickname = console.collectStringInput(Messages.INPUT_NICKNAME.getMessage());
        return CreateUserRequest.of(nickname);
    }

    //TODO: 메세지 어찌할지
    public Long collectIdInput() {
        return console.collectLongInput(Messages.INPUT_ID.getMessage());
    }

    public UUID collectUUIDInput() {
        return console.collectUUIDInput(Messages.INPUT_ID.getMessage());
    }

    public UpdateVoucherMediatorRequest collectUpdateVoucherInput() {
        UUID id = console.collectUUIDInput(Messages.INPUT_ID.getMessage());
        String voucherType = console.collectStringInput(Messages.VOUCHER_UPDATE_TYPE.getMessage());
        int amount = console.collectIntegerInput(Messages.VOUCHER_UPDATE_AMOUNT.getMessage());
        return UpdateVoucherMediatorRequest.of(id, voucherType, amount);
    }

    public CreateUserVoucherWalletRequest collectUserVoucherWalletInput() {
        String userNickname = console.collectStringInput(Messages.INPUT_NICKNAME.getMessage());
        UUID voucherId = console.collectUUIDInput(Messages.INPUT_ID.getMessage());
        return CreateUserVoucherWalletRequest.of(userNickname, voucherId);
    }

    public String collectNicknameInput() {
        return console.collectStringInput(Messages.INPUT_NICKNAME.getMessage());
    }


    public void displayMessage(String message) {
        console.print(message);
    }
}
