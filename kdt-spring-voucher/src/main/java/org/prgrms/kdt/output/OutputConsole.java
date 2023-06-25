package org.prgrms.kdt.output;

import org.springframework.stereotype.Component;

@Component
public class OutputConsole implements Output {

    @Override
    public void displayMenu() {
        System.out.println("=== Voucher Program ===");
        printMenuLine("exit", "the program");
        printMenuLine("create", "a new voucher");
        printMenuLine("list", "all vouchers");
        System.out.println();
    }

    @Override
    public void displayUserInputLine() {
        System.out.print("Please Type command : ");
    }

    private void printMenuLine(String userInput,String inputExplanation){
        System.out.printf("Type %s to %s %s.%n",userInput,userInput,inputExplanation);
    }
}
