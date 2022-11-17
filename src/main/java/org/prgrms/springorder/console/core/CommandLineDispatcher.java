package org.prgrms.springorder.console.core;

import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.console.io.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineDispatcher.class);

    private final RequestHandler requestHandler;

    public CommandLineDispatcher(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public Response request(Command command, Request request) {
        logger.info("request - command :  {}, requestBody : {}", command, request);
        return requestHandler.handle(command).apply(request);
    }

}
