package org.programmers.springbootbasic.console.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.Model;
import org.programmers.springbootbasic.console.ModelAndView;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.service.VoucherService;
import org.programmers.springbootbasic.voucher.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.Voucher;
import org.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.*;
import static org.programmers.springbootbasic.console.command.InputCommand.*;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_COMPLETE;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherController implements Controller {

    private final VoucherService voucherService;
    private static final String redirectViewPath = "create/";
    private static final Map<String, Command> commandList = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void initCommandList() {
        commandList.put(CREATE.getName(), CREATE);
        commandList.put(CREATE_AMOUNT.getName(), CREATE_AMOUNT);
        commandList.put(CREATE_COMPLETE.getName(), CREATE_COMPLETE);
        commandList.put(LIST.getName(), LIST);
    }

    @Override
    public ModelAndView process(Command command, Model model) {
        log.info("processing command {} at Controller", command);
        return (command instanceof InputCommand) ?
                processInputCommand((InputCommand) command, model) :
                processRedirectCommand((RedirectCommand) command, model);
    }

    @Override
    public boolean supports(Command command) {
        for (var supportingCommand : commandList.values()) {
            if (command == supportingCommand) {
                log.trace("Controller: {} supports command: {}.", this, command);
                return true;
            }
        }
        return false;
    }

    private ModelAndView processInputCommand(InputCommand command, Model model) {
        switch (command) {
            case CREATE:
                return create(CREATE, model);
            case LIST:
                return list(LIST, model);
        }
        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "컨트롤러가 해당 커맨드를 처리하지 못 합니다. 컨트롤러 매핑이 잘못되었는지 확인해주세요.");
    }

    private ModelAndView processRedirectCommand(RedirectCommand command, Model model) {
        switch (command) {
            case CREATE_AMOUNT:
                return createAmount(CREATE_AMOUNT, model);
            case CREATE_COMPLETE:
                return createComplete(CREATE_COMPLETE, model);
        }
        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "컨트롤러가 해당 커맨드를 처리하지 못 합니다. 컨트롤러 매핑이 잘못되었는지 확인해주세요.");
    }

    private ModelAndView create(InputCommand command, Model model) {
        List<String> voucherTypesInformation = new ArrayList<>();
        for (var voucherType : VoucherType.values()) {
            voucherTypesInformation.add(voucherType.explainThisType());
        }

        model.addAttributes("allVoucherTypes", voucherTypesInformation);
        model.setRedirectLink("create-amount");
        model.setInputEnvironment("type", "create");

        return new ModelAndView(model, redirectViewPath + command.getName(), INPUT);
    }

    private ModelAndView createAmount(RedirectCommand command, Model model) {
        //TODO: 컨버터 개발하여 아래 로직 대체하기1
        String ordinalString = (String) model.getAttributes("type");
        int ordinal = Integer.parseInt(ordinalString);

        model.addAttributes("amount", VoucherType.findTypeByOrdinal(ordinal).getDiscountUnitName());
        model.setRedirectLink("create-complete");
        model.setInputEnvironment("amount", "create-amount");

        return new ModelAndView(model, redirectViewPath + command.getName(), INPUT);
    }

    private ModelAndView createComplete(RedirectCommand command, Model model) {
        Voucher voucher;

        //TODO: 컨버터 개발하여 아래 로직 대체하기2
        String ordinalString = (String) model.getAttributes("type");
        int ordinal = Integer.parseInt(ordinalString);
        String amountString = (String) model.getAttributes("amount");
        int amount = Integer.parseInt(amountString);

        Class<? extends Voucher> type = VoucherType.findTypeByOrdinal(ordinal).getType();

        if (type.equals(FixedDiscountVoucher.class)) {
            voucher = new FixedDiscountVoucher(UUID.randomUUID(), amount);
        } else if (type.equals(RateDiscountVoucher.class)) {
            voucher = new RateDiscountVoucher(UUID.randomUUID(), amount);
        } else {
            log.info("Illegal type of voucher. No corresponding voucher type exist.");
            throw new IllegalArgumentException("Illegal type of voucher. No corresponding voucher type exist.");
        }
        voucherService.registerVoucher(voucher);

        model.setNoRedirectLink();
        model.clear();

        return new ModelAndView(model, redirectViewPath + command.getName(), PROCEED);
    }

    private ModelAndView list(InputCommand command, Model model) {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) {
            model.addAttributes("allVouchersInformation", "저장된 바우처가 없습니다.");
            return new ModelAndView(model, command.getName(), PROCEED);
        }
        List<String> allVouchersInformation = new ArrayList<>(vouchers.size());

        for (Voucher voucher : vouchers) {
            allVouchersInformation.add(VoucherType.dataOfVoucher(voucher));
        }

        model.addAttributes("allVouchersInformation", allVouchersInformation);

        return new ModelAndView(model, command.getName(), PROCEED);
    }
}
