package org.prgrms.kdtspringorder.io.abstraction;

import org.prgrms.kdtspringorder.io.domain.Command;

@FunctionalInterface
public interface Input {
  Command read();
}
