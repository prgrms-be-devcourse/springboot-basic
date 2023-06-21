package com.dev.voucherproject.service;


import com.dev.voucherproject.service.menus.Menu;
import com.dev.voucherproject.service.menus.SelectMenuExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuService {
    private final List<SelectMenuExecutor> executors;

    public MenuService(SelectMenuExecutor... executors) {
        this.executors = new ArrayList<>(Arrays.asList(executors));
    }

    public void menuExecute(Menu menu) {
        for (SelectMenuExecutor executor : executors) {
            executor.execute(menu);
        }
    }
}
