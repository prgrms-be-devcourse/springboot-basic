package com.dojinyou.devcourse.voucherapplication;

import com.dojinyou.devcourse.voucherapplication.view.InputView;
import com.dojinyou.devcourse.voucherapplication.view.OutputView;
import com.dojinyou.devcourse.voucherapplication.voucher.Entity.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.Entity.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.VoucherController;
import com.dojinyou.devcourse.voucherapplication.voucher.Entity.VoucherType;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController {
    private static final String INTRO_MESSAGE = "========Voucher Program=========";
    private static final String SEPARATOR_MESSAGE = "=================================";
    private static final String ERROR_MESSAGE_BAD_COMMAND = "잘못된 명령어입니다. 다시 요청하시기 바랍니다.";
    private static final String ERROR_MESSAGE_BAD_VOUCHER_TYPE = "잘못된 바우처 타입입니다.";
    private static final String REQUEST_MESSAGE_VOUCHER_TYPE = "Voucher Type(FIXED, PERCENT) 중 하나를 입력하세요: ";
    private static final String REQUEST_MESSAGE_FIXED_AMOUNT = "Fixed Amount값을 입력해주세요(1~1,000,000): ";
    private static final String REQUEST_MESSAGE_PERCENT_AMOUNT = "Percent Amount값을 입력해주세요(1~100): ";

    private final InputView<String> inputView;
    private final OutputView<String> outputView;
    private final VoucherController voucherController;

    public ApplicationController(InputView<String> inputView, OutputView<String> outputView, VoucherController voucherController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherController = voucherController;
    }

    public void run() {
        outputView.display(INTRO_MESSAGE);
        while (true) {
            try {
                outputView.display(Command.getCommandList());
                String userCommandString = inputView.getUserInput();
                Command userCommand = Command.of(userCommandString.toUpperCase());
                Response response = commandHandle(userCommand);

                if (response.getData().equals(Command.EXIT))
                    return;

                outputView.display(response.toString());

            } catch (IllegalArgumentException e) {
                outputView.display(e.getMessage());
            } finally {
                outputView.display(SEPARATOR_MESSAGE);
            }
        }
    }

    public Response commandHandle(Command command) {
        if (command == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_BAD_COMMAND);
        }
        switch (command) {
            case EXIT:
                outputView.display(Command.EXIT.toString());
                return new Response(Response.State.SUCCESS, Command.EXIT);
            case CREATE:
                VoucherType voucherType = getVoucherType();
                VoucherAmount amount = getVoucherAmount(voucherType);
                Voucher.Request requestDto = new Voucher.Request(voucherType, amount);

                return voucherController.create(requestDto);
            case LIST:
                return voucherController.findAll();

            default:
                throw new IllegalArgumentException(ERROR_MESSAGE_BAD_COMMAND);
        }
    }

    private VoucherType getVoucherType() {
        outputView.display(REQUEST_MESSAGE_VOUCHER_TYPE);
        String userInput = inputView.getUserInput();
        return VoucherType.of(userInput.toUpperCase());
    }

    private VoucherAmount getVoucherAmount(VoucherType voucherType) {
        if (voucherType == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_BAD_VOUCHER_TYPE);
        }
        switch (voucherType) {
            case FIXED:
                outputView.display(REQUEST_MESSAGE_FIXED_AMOUNT);
                break;
            case PERCENT:
                outputView.display(REQUEST_MESSAGE_PERCENT_AMOUNT);
                break;
            default:
                throw new IllegalArgumentException(ERROR_MESSAGE_BAD_VOUCHER_TYPE);

        }
        int userInput = Integer.parseInt(inputView.getUserInput());
        return VoucherAmount.from(voucherType, userInput);
    }
}
