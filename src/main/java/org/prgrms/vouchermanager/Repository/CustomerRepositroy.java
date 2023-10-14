package org.prgrms.vouchermanager.Repository;

import java.util.List;
import java.util.Map;

public interface CustomerRepositroy {
    List<Map<String, String>> findAll();
}
