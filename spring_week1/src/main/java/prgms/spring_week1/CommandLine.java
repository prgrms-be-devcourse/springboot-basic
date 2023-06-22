package prgms.spring_week1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.option.Option;

import java.io.IOException;

@Component
public class CommandLine implements Runnable{
    private Input input;
    private Output output;

    public CommandLine(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            String selectOption = null;
            output.printInputMessage();
            try {
                selectOption = input.inputTextOption();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            switch (findMenuName(selectOption)) {
                case EXIT -> System.out.println("exit");
                case CREATE -> System.out.println("create");
                case LIST -> System.out.println("list");
                default -> System.out.println("wrong");
            }
        }
    }

    private Option findMenuName(String inputText){
        return Option.findMenuType(inputText);
    }
}
