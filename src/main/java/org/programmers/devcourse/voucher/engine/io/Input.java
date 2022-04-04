package org.programmers.devcourse.voucher.engine.io;


import java.io.IOException;
import java.util.Optional;
import org.programmers.devcourse.voucher.engine.Selection;

public interface Input extends AutoCloseable {

  Optional<Selection> getSelection() throws IOException;
}
