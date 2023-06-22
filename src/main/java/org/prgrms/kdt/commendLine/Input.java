package org.prgrms.kdt.commendLine;

import java.io.IOException;

public interface Input {
    String getUserMenu() throws IOException;
    String getVoucherTypes() throws IOException;

}
