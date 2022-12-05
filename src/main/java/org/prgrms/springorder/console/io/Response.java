package org.prgrms.springorder.console.io;

public interface Response {

    String getResponse();

    Response OK = new StringResponse("ok");

}
