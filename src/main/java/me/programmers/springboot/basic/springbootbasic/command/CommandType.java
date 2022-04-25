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
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public enum CommandType {
    EXIT {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(ExitCommand.class);
        }
    },
    CREATE {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CreateVoucherCommand.class);
        }
    },
    LIST {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(ShowVoucherCommand.class);
        }
    },
    UPDATE {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(UpdateVoucherCommand.class);
        }
    },
    DELETE {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(DeleteVoucherCommand.class);
        }
    },
    CUSTOMER_INSERT {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CustomerInsertCommand.class);
        }
    },
    CUSTOMER_UPDATE {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CustomerUpdateCommand.class);
        }
    },
    CUSTOMER_LIST {

        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CustomerFindAllCommand.class);
        }
    },
    CUSTOMER_FINDBY_ID {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return null;
        }
    },
    CUSTOMER_FINDBY_NAME {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return null;
        }
    },
    CUSTOMER_FINDBY_EMAIL {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CustomerFindByEmailCommand.class);
        }
    },
    CUSTOMER_DELETE {
        @Override
        public CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context) {
            return context.getBean(CustomerDeleteCommand.class);
        }
    };

    public static CommandType getCommandType(String menuCommand) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어 입력 " + menuCommand));
    }

    public abstract CommandStrategy getCommandStrategy(AnnotationConfigApplicationContext context);
}
