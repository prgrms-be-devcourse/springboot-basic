package org.programmers.springbootbasic.console.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.ConsoleModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT_AND_REDIRECT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.InputCommand.CREATE_VOUCHER;
import static org.programmers.springbootbasic.console.command.InputCommand.LIST_VOUCHER;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_VOUCHER_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_VOUCHER_COMPLETE;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.dataOf;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.findTypeByOrdinal;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherHandler implements Handler {

    private final VoucherService voucherService;
    private static final String VIEW_PATH = "voucher/";
    private static final Map<String, Command> COMMANDS = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void initCommandList() {
        COMMANDS.put(CREATE_VOUCHER.getViewName(), CREATE_VOUCHER);
        COMMANDS.put(CREATE_VOUCHER_AMOUNT.getViewName(), CREATE_VOUCHER_AMOUNT);
        COMMANDS.put(CREATE_VOUCHER_COMPLETE.getViewName(), CREATE_VOUCHER_COMPLETE);
        COMMANDS.put(LIST_VOUCHER.getViewName(), LIST_VOUCHER);
    }

    @Override
    public boolean supports(Command command) {
        for (var supportingCommand : COMMANDS.values()) {
            if (command == supportingCommand) {
                log.trace("Controller: {} supports command: {}.", this, command);
                return true;
            }
        }
        return false;
    }

    @Override
    public ConsoleModelAndView handleRequest(ConsoleRequest request) {
        var command = request.getCommand();
        log.info("processing command {} at Controller", command);

        if (command == CREATE_VOUCHER) {
            return create(request);
        }
        if (command == CREATE_VOUCHER_AMOUNT) {
            return createAmount(request);
        }
        if (command == CREATE_VOUCHER_COMPLETE) {
            return createComplete(request);
        }
        if (command == LIST_VOUCHER) {
            return list(request);
        }
        throw new IllegalStateException(
                "컨트롤러가 해당 커맨드를 처리하지 못 합니다. 컨트롤러 매핑이 잘못되었는지 확인해주세요.");
    }

    private ConsoleModelAndView create(ConsoleRequest request) {
        var model = request.getConsoleModel();

        List<String> voucherTypesDescription = getAllVoucherTypeDescription();

        model.addAttributes("allVoucherTypes", voucherTypesDescription);
        model.setInputSignature("type");
        model.setRedirectLink(CREATE_VOUCHER_AMOUNT);

        return new ConsoleModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), INPUT_AND_REDIRECT);
    }

    private List<String> getAllVoucherTypeDescription() {
        List<String> voucherTypesInformation = new ArrayList<>();
        for (var voucherType : VoucherType.values()) {
            voucherTypesInformation.add(voucherType.explainThisType());
        }
        return voucherTypesInformation;
    }

    private ConsoleModelAndView createAmount(ConsoleRequest request) {
        var model = request.getConsoleModel();

        VoucherType voucherType = getVoucherTypeFromModel(model.getAttributes("type"));
        model.addAttributes("voucherType", voucherType.getName());

        model.addAttributes("amountUnit", voucherType.getDiscountUnitMessage());
        model.setRedirectLink(CREATE_VOUCHER_COMPLETE);
        model.setInputSignature("amount");

        return new ConsoleModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), INPUT_AND_REDIRECT);
    }

    private ConsoleModelAndView createComplete(ConsoleRequest request) {
        var model = request.getConsoleModel();

        int amount = getDiscountAmountFromModel(model.getAttributes("amount"));
        var voucherType = getVoucherTypeFromModel(model.getAttributes("type"));

        voucherService.createVoucher(amount, voucherType);

        model.clear();

        return new ConsoleModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), PROCEED);
    }

    private VoucherType getVoucherTypeFromModel(Object modelAttribute) {
        String voucherTypeInput = (String) modelAttribute;
        int ordinal = Integer.parseInt(voucherTypeInput);
        return findTypeByOrdinal(ordinal);
    }

    private int getDiscountAmountFromModel(Object modelAttribute) {
        String amountString = (String) modelAttribute;
        return Integer.parseInt(amountString);
    }

    private ConsoleModelAndView list(ConsoleRequest request) {
        var model = request.getConsoleModel();

        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) {
            model.addAttributes("allVouchersInformation", "저장된 바우처가 없습니다.");
            return new ConsoleModelAndView(model, request.getCommand().getViewName(), PROCEED);
        }
        List<String> allVouchersInformation = new ArrayList<>(vouchers.size());

        for (Voucher voucher : vouchers) {
            allVouchersInformation.add(dataOf(voucher));
        }

        model.addAttributes("allVouchersInformation", allVouchersInformation);

        return new ConsoleModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), PROCEED);
    }
}