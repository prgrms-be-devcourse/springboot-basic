package org.prgrms.kdt.io;

import java.util.Optional;
import java.util.UUID;

public interface Input {

    String input();

    long inputLong();

    UUID inputUUID();
}
