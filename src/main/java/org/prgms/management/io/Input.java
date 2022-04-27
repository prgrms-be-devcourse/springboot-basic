package org.prgms.management.io;

import org.springframework.stereotype.Component;

@Component
public interface Input {
    String getInput(String text);

    Integer getInputInteger(String text);
}
