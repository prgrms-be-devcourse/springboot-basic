package org.prgrms.application;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public enum CommandType {
    EXIT,
    CREATE,
    LIST
}
