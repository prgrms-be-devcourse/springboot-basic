package com.wonu606.vouchermanager.descriptionGenerator;

import java.util.List;
import java.util.stream.IntStream;

public class MenuDescriptionGenerator {

    String lineFormat = "Type %s to %s the program.\n";

    public String generate(List<String> commandList) {
        StringBuilder builder = new StringBuilder();

        commandList.forEach(command -> builder.append(generateLine(command)));

        return builder.toString();
    }

    private String generateLine(String command) {
        return String.format(lineFormat, command, command);
    }
}
