package devcourse.springbootbasic.commandline.function;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public enum Function {

    EXIT("exit", "Type exit to exit the program.", FunctionHandler::exit),
    CREATE_VOUCHER("create", "Type create to create a new voucher.", FunctionHandler::createVoucher),
    LIST_VOUCHERS("list", "Type list to list all vouchers.", FunctionHandler::listAllVouchers),
    LIST_BLACKLISTED_USERS("blacklist", "Type blacklist to list all blacklisted users.", FunctionHandler::listAllBlacklistedUsers);

    private final String functionString;
    @Getter
    private final String description;
    private final Consumer<FunctionHandler> functionConsumer;

    public static Optional<Function> fromString(String functionString) {
        return Arrays.stream(values())
                .filter(function -> function.functionString.equals(functionString))
                .findFirst();
    }

    public void execute(FunctionHandler functionHandler) {
        this.functionConsumer.accept(functionHandler);
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
