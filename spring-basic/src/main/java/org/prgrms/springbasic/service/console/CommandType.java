package org.prgrms.springbasic.service.console;

public enum CommandType {

    REGISTER {
        @Override
        public void action(ConsoleService consoleService) {
            consoleService.register();
        }
    },
    CUSTOMERS {
        @Override
        public void action(ConsoleService consoleService) {
            consoleService.customers();
        }
    },
    CREATE {
        @Override
        public void action(ConsoleService consoleService) {
            consoleService.create();
        }
    },
    LIST {
        @Override
        public void action(ConsoleService consoleService) {
            consoleService.vouchers();
        }
    },
    EXIT {
        @Override
        public void action(ConsoleService consoleService) {
            consoleService.exit();
        }
    };

    public abstract void action(ConsoleService consoleService);
}
