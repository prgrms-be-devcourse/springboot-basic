package com.example.demo.customer.presentation;

import com.example.demo.common.command.Command;
import com.example.demo.common.io.Output;
import com.example.demo.customer.presentation.dto.BlackCustomer;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("blacklist")
public class BlackListCommand implements Command {

    private final Set<BlackCustomer> blackList;

    private final Output output;


    public BlackListCommand(Set<BlackCustomer> blackList, Output output) {
        this.blackList = blackList;
        this.output = output;
    }

    @Override
    public void execute() {
        output.printLine("=== Blacklist ===");
        blackList.stream()
                .map(v -> v.getEmail())
                .forEach(output::printLine);
    }
}
