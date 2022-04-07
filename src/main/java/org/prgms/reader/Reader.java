package org.prgms.reader;

import org.prgms.user.User;

import java.io.File;
import java.util.List;

public interface Reader {
    List<User> readFile(File file) throws Exception;
}
