package com.example.voucher_manager.io;

public class Console {
    private final Input input;
    private final Output output;
    private final InputValidator inputValidator;
    private final CommandOperator commandOperator;

    public Console(Input input, Output output, InputValidator inputValidator, CommandOperator commandOperator) {
        this.input = input;
        this.output = output;
        this.inputValidator = inputValidator;
        this.commandOperator = commandOperator;
    }

    public CommandType inputCommand() {
        printMenu();
        String userInput = input.input(" >> ");
        CommandType command = inputValidator.validate(userInput);

        // ERROR인지 체크하고
        // ERROR라면 조기 리턴해서 다시 작업 수행
        if (command.equals(CommandType.ERROR)){
            output.printError();
            return command;
        }

        // ERROR가 아니라면 CommandOperator에게 넘겨서 메뉴별로 작업 분기

        switch (command) {
            case CREATE -> commandOperator.create();
            case LIST -> commandOperator.getList();
            case EXIT -> output.exit();
        }

        return command;
    }

    private void printMenu() {
        output.print("""
               === Voucher Program ===
               Type **exit** to exit the program.
               Type **create** to create a new voucher.
               Type **list** to list all vouchers.
                """);
    }
}
