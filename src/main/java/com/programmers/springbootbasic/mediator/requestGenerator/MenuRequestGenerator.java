package com.programmers.springbootbasic.mediator.requestGenerator;

import com.programmers.springbootbasic.mediator.ConsoleRequest;

public interface MenuRequestGenerator {

    String getMenuCommand();

    ConsoleRequest generateRequest();
}
