package org.programmers.spbw1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {
    static Input input;
    static Output output;

    @BeforeAll
    static void init(){
        input = new Console();
        output = new Console();
    }

    @AfterAll
    static void cleanUp(){

    }

    @Test
    @DisplayName("Input test")
    public void simpleInputTest(){
        String inputString = "input string";

    }
}