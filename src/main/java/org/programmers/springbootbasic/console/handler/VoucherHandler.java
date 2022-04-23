package org.programmers.springbootbasic.console.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT_AND_REDIRECT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.InputCommand.CREATE;
import static org.programmers.springbootbasic.console.command.InputCommand.LIST;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_COMPLETE;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherHandler implements Handler {

    private final VoucherService voucherService;
    private static final String VIEW_PATH = "create/";
    private static final Map<String, Command> COMMANDS = new ConcurrentHashMap<>();

    @PostConstruct
    @Override
    public void initCommandList() {
        COMMANDS.put(CREATE.getViewName(), CREATE);
        COMMANDS.put(CREATE_AMOUNT.getViewName(), CREATE_AMOUNT);
        COMMANDS.put(CREATE_COMPLETE.getViewName(), CREATE_COMPLETE);
        COMMANDS.put(LIST.getViewName(), LIST);
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
    public ModelAndView handleRequest(ConsoleRequest request) {
        var command = request.getCommand();
        log.info("processing command {} at Controller", command);

        if (command == CREATE) {
            return create(request);
        }
        if (command == CREATE_AMOUNT) {
            return createAmount(request);
        }
        if (command == CREATE_COMPLETE) {
            return createComplete(request);
        }
        if (command == LIST) {
            return list(request);
        }
        log.error("No controller handling command {} exist.", command);
        throw new IllegalStateException(
                "컨트롤러가 해당 커맨드를 처리하지 못 합니다. 컨트롤러 매핑이 잘못되었는지 확인해주세요.");
    }

    private ModelAndView create(ConsoleRequest request) {
        var model = request.getModel();

        List<String> voucherTypesDescription = getAllVoucherTypeDescription();

        model.addAttributes("allVoucherTypes", voucherTypesDescription);
        model.setInputSignature("type");
        model.setRedirectLink(CREATE_AMOUNT);

        return new ModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), INPUT_AND_REDIRECT);
    }

    private List<String> getAllVoucherTypeDescription() {
        List<String> voucherTypesInformation = new ArrayList<>();
        for (var voucherType : VoucherType.values()) {
            voucherTypesInformation.add(voucherType.explainThisType());
        }
        return voucherTypesInformation;
    }

    private ModelAndView createAmount(ConsoleRequest request) {
        var model = request.getModel();

        VoucherType voucherType = getVoucherTypeFromModel(model.getAttributes("type"));
        model.addAttributes("voucherType", voucherType.getName());

        model.addAttributes("amountUnit", voucherType.getDiscountUnitMessage());
        model.setRedirectLink(CREATE_COMPLETE);
        model.setInputSignature("amount");

        return new ModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), INPUT_AND_REDIRECT);
    }

    private ModelAndView createComplete(ConsoleRequest request) {
        var model = request.getModel();

        int amount = getDiscountAmountFromModel(model.getAttributes("amount"));
        var voucherType = getVoucherTypeFromModel(model.getAttributes("type"));

        voucherService.registerVoucher(amount, voucherType);

        model.clear();

        return new ModelAndView(model, VIEW_PATH + request.getCommand().getViewName(), PROCEED);
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

    private ModelAndView list(ConsoleRequest request) {
        var model = request.getModel();

        List<Voucher> vouchers = voucherService.getAllVouchers();
        if (vouchers.isEmpty()) {
            model.addAttributes("allVouchersInformation", "저장된 바우처가 없습니다.");
            return new ModelAndView(model, request.getCommand().getViewName(), PROCEED);
        }
        List<String> allVouchersInformation = new ArrayList<>(vouchers.size());

        for (Voucher voucher : vouchers) {
            allVouchersInformation.add(dataOfVoucher(voucher));
        }

        model.addAttributes("allVouchersInformation", allVouchersInformation);

        return new ModelAndView(model, request.getCommand().getViewName(), PROCEED);
    }
}