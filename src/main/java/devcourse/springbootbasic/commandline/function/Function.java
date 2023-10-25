package devcourse.springbootbasic.commandline.function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public enum Function {

    CREATE_VOUCHER("1", "Create a new voucher.", FunctionHandler::createVoucher),
    LIST_VOUCHERS("2", "List all vouchers.", FunctionHandler::listAllVouchers),
    CREATE_CUSTOMER("3", "Create a new customer.", FunctionHandler::createCustomer),
    LIST_BLACKLISTED_CUSTOMERS("4", "List all blacklisted customers.", FunctionHandler::findAllBlacklistedCustomers),
    UPDATE_BLACKLIST_STATUS("5", "Update blacklist status of a customer.", FunctionHandler::updateBlacklistStatus),
    EXIT("9", "Exit the program.", FunctionHandler::exit);

    private final String code;
    private final String description;
    private final Consumer<FunctionHandler> functionConsumer;

    public static Optional<Function> fromCode(String functionString) {
        return Arrays.stream(values())
                .filter(function -> function.code.equalsIgnoreCase(functionString.trim()))
                .findFirst();
    }

    public void execute(FunctionHandler functionHandler) {
        this.functionConsumer.accept(functionHandler);
    }

    @Override
    public String toString() {
        return this.code + ". " + this.description;
    }
}
