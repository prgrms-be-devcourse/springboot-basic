package com.example.springbootbasic.io;

import java.util.Optional;

public interface Input {
    Optional<Command> getInputCommand(String prompt);
}
