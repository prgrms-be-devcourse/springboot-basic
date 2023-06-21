package com.dev.voucherproject.controller;


import com.dev.voucherproject.model.Menu;
import com.dev.voucherproject.controller.menus.SelectMenuExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MenuController {
    private final List<SelectMenuExecutor> executors;

    public MenuController(SelectMenuExecutor... executors) {
        this.executors = new ArrayList<>(Arrays.asList(executors));
    }

    public void menuExecute(Menu menu) {
        for (SelectMenuExecutor executor : executors) {
            executor.execute(menu);
        }
    }
}
