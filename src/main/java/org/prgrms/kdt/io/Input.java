package org.prgrms.kdt.io;

import java.util.Optional;
import org.prgrms.kdt.command.CommandType;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 6:09 오후
 */
public interface Input {

    Optional<CommandType> inputCommand();

    String[] inputVoucher();

}
