package me.programmers.springboot.basic.springbootbasic.command;

import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerDeleteCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerFindAllCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerFindByEmailCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerInsertCommand;
import me.programmers.springboot.basic.springbootbasic.command.customer.CustomerUpdateCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.CreateVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.DeleteVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.ShowVoucherCommand;
import me.programmers.springboot.basic.springbootbasic.command.voucher.UpdateVoucherCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.function.Function;

public enum CommandType {
    EXIT (context ->  context.getBean(ExitCommand.class)),
    CREATE (context -> context.getBean(CreateVoucherCommand.class)),
    LIST (context -> context.getBean(ShowVoucherCommand.class)),
    UPDATE  (context -> context.getBean(UpdateVoucherCommand.class)),
    DELETE (context -> context.getBean(DeleteVoucherCommand.class)),
    CUSTOMER_INSERT (context -> context.getBean(CustomerInsertCommand.class)),
    CUSTOMER_UPDATE (context -> context.getBean(CustomerUpdateCommand.class)),
    CUSTOMER_LIST (context -> context.getBean(CustomerFindAllCommand.class)),
    CUSTOMER_FINDBY_EMAIL (context -> context.getBean(CustomerFindByEmailCommand.class)),
    CUSTOMER_DELETE (context -> context.getBean(CustomerDeleteCommand.class));

    private Function<AnnotationConfigApplicationContext, CommandStrategy> job;
    CommandType(Function<AnnotationConfigApplicationContext, CommandStrategy> job) {
        this.job = job;
    }

    public CommandStrategy execute(AnnotationConfigApplicationContext context) {
        return job.apply(context);
    }

    public static CommandType getCommandType(String menuCommand) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어 입력 " + menuCommand));
    }

}
