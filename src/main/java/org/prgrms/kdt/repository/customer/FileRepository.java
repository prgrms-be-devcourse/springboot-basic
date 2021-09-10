package org.prgrms.kdt.repository.customer;

import java.io.IOException;

public interface FileRepository {

    void loadFile() throws ClassNotFoundException, IOException;

    void writeFile() throws IOException;

}
