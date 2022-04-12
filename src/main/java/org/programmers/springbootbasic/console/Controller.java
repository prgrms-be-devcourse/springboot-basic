package org.programmers.springbootbasic.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.service.VoucherService;
import org.programmers.springbootbasic.voucher.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.Voucher;
import org.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.programmers.springbootbasic.console.command.InputCommand.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.*;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_COMPLETE;

@Slf4j
@Component
@RequiredArgsConstructor
//TODO: 컨트롤러 분리
class Controller {

    private final VoucherService voucherService;

    ModelAndView process(Command command, Model model) {
        log.info("processing command {} at Controller", command);
        return (command instanceof InputCommand) ?
                processInputCommand((InputCommand) command, model) :
                processRedirectCommand((RedirectCommand) command, model);
    }

    private ModelAndView processInputCommand(InputCommand command, Model model) {
        switch (command) {
            case HOME:
                return home(HOME, model);
            case CREATE:
                return create(CREATE, model);
            case LIST:
                return list(LIST, model);
            case EXIT:
                return exit(EXIT, model);
            default:
                return help(HELP, model);
        }
    }

    private ModelAndView processRedirectCommand(RedirectCommand command, Model model) {
        switch (command) {
            case CREATE_AMOUNT:
                return createAmount(CREATE_AMOUNT, model);
            case CREATE_COMPLETE:
                return createComplete(CREATE_COMPLETE, model);
            default:
                return help(HELP, model);
        }
    }

    private ModelAndView home(InputCommand command, Model model) {
        return new ModelAndView(model, command.getName(), OK);
    }

    private ModelAndView create(InputCommand command, Model model) {
        List<String> voucherTypesInformation = new ArrayList<>();
        for (var voucherType : VoucherType.values()) {
            voucherTypesInformation.add(voucherType.explainThisType());
        }

        model.addAttributes("allVoucherTypes", voucherTypesInformation);
        model.setRedirectLink("create-amount");
        model.setInputSignature("type");

        return new ModelAndView(model, "create/" + command.getName(), INPUT);
    }

    private ModelAndView createAmount(RedirectCommand command, Model model) {
        //TODO: 컨버터 개발하여 아래 로직 대체하기1
        String ordinalString = (String) model.getAttributes("type");
        int ordinal = Integer.parseInt(ordinalString);

        model.addAttributes("amount", VoucherType.findTypeByOrdinal(ordinal).getDiscountUnitName());
        model.setRedirectLink("create-complete");
        model.setInputSignature("amount");

        return new ModelAndView(model, "create/" + command.getName(), INPUT);
    }

    private ModelAndView createComplete(RedirectCommand command, Model model) {
        Voucher voucher;

        //TODO: 컨버터 개발하여 아래 로직 대체하기
        String ordinalString = (String) model.getAttributes("type");
        int ordinal = Integer.parseInt(ordinalString);
        String amountString = (String) model.getAttributes("amount");
        long amount = Long.parseLong(amountString);

        Class<? extends Voucher> type = VoucherType.findTypeByOrdinal(ordinal).getType();

        if (type.equals(FixedDiscountVoucher.class)) {
            voucher = new FixedDiscountVoucher(UUID.randomUUID(), amount);
        }
        else if (type.equals(RateDiscountVoucher.class)) {
            voucher = new RateDiscountVoucher(UUID.randomUUID(), amount);
        }
        else {
            log.info("Illegal type of voucher. No corresponding voucher type exist.");
            throw new IllegalArgumentException("Illegal type of voucher. No corresponding voucher type exist.");
        }
        voucherService.registerVoucher(voucher);

        model.setNoRedirectLink();
        model.clear();

        return new ModelAndView(model, "create/" + command.getName(), OK);
    }

    private ModelAndView list(InputCommand command, Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        List<String> allVouchersInformation = new ArrayList<>(vouchers.size());

        for (Voucher voucher : vouchers) {
            allVouchersInformation.add(VoucherType.dataOfVoucher(voucher));
        }

        model.addAttributes("allVouchersInformation", allVouchersInformation);

        return new ModelAndView(model, command.getName(), OK);
    }

    private ModelAndView exit(InputCommand command, Model model) {
        return new ModelAndView(model, command.getName(), STOP);
    }

    private ModelAndView help(InputCommand command, Model model) {
        var commands = InputCommand.values();
        List<String> allCommandsInformation = new ArrayList<>();

        for (InputCommand eachCommand : commands) {
            allCommandsInformation.add(eachCommand.getCommandInformation());
        }

        model.addAttributes("allCommandsInformation", allCommandsInformation);

        return new ModelAndView(model, command.getName(), OK);
    }

}
