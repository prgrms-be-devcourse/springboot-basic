package org.prgrms.springorder.console.io;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListResponse implements Response {

    private final String message;

    public ListResponse(List<?> message) {
        this.message = message.stream().map(Objects::toString)
            .collect(Collectors.joining("\n"));;
    }

    @Override
    public String getResponse() {
        return message;
    }
}
