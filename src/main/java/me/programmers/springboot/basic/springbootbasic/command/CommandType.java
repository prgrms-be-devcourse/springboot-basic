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
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;

import java.util.Arrays;

public enum CommandType {
    EXIT {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new ExitCommand();
        }
    },
    CREATE {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CreateVoucherCommand(voucherService);
        }
    },
    LIST {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new ShowVoucherCommand(voucherService);
        }
    },
    UPDATE {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new UpdateVoucherCommand(voucherService);
        }
    },
    DELETE {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new DeleteVoucherCommand(voucherService);
        }
    },
    CUSTOMER_INSERT {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CustomerInsertCommand(customerService);
        }
    },
    CUSTOMER_UPDATE {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CustomerUpdateCommand(customerService);
        }
    },
    CUSTOMER_LIST {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CustomerFindAllCommand(customerService);
        }
    },
    CUSTOMER_FINDBY_ID {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return null;
        }
    },
    CUSTOMER_FINDBY_NAME {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return null;
        }
    },
    CUSTOMER_FINDBY_EMAIL {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CustomerFindByEmailCommand(customerService);
        }
    },
    CUSTOMER_DELETE {
        @Override
        public CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService) {
            return new CustomerDeleteCommand(customerService);
        }
    };

    public static CommandType getCommandType(String menuCommand) {
        return Arrays.stream(CommandType.values())
                .filter(command -> command.name().equals(menuCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 메뉴 명령어 입력 " + menuCommand));
    }

    public abstract CommandStrategy getCommandStrategy(JdbcVoucherService voucherService, CustomerService customerService);
}
