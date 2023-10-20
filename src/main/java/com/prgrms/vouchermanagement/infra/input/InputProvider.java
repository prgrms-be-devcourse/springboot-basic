package com.prgrms.vouchermanagement.infra.input;

import java.io.IOException;

public interface InputProvider {

    String getMenuType() throws IOException;
}
