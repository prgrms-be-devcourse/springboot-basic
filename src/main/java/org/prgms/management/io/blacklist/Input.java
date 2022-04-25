package org.prgms.management.io.blacklist;

import org.springframework.stereotype.Component;

@Component
public interface Input {
    String getInput(String text);
}
